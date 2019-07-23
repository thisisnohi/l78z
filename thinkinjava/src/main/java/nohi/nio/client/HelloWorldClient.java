package nohi.nio.client;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;

public class HelloWorldClient {
	static int size = 10;
	static String charsetName = "UTF-8";
	static InetSocketAddress ip = new InetSocketAddress("localhost",8888);
	static CharsetEncoder encoder = Charset.forName(charsetName).newEncoder();
	
	static class Message implements Runnable{
		private String name;
		String msg = "";
		
		public Message(String index){
			this.name = index;
		}
		
		@Override
		public void run() {

			try {
				long start = System.currentTimeMillis();
				System.out.println("start:" + start);
				
				SocketChannel client = SocketChannel.open();
				
				client.configureBlocking(false);
				Selector selector = Selector.open();
				client.register(selector, SelectionKey.OP_CONNECT);
				client.connect(ip);
				
				ByteBuffer buffer = ByteBuffer.allocate(8 * 1024);
				int total = 0;
				
				_WHILE: while (true) {
					selector.select();
					System.out.println("select:");
					
					Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
					
					while (iter.hasNext()) {
						SelectionKey key = iter.next();
						iter.remove();
						
						SocketChannel channel = (SocketChannel)key.channel();
						if (key.isConnectable()) {
							System.out.println("isConnectable:");
							if (channel.isConnectionPending()) {
								channel.finishConnect();
								
								channel.write(encoder.encode(CharBuffer.wrap(name)));
								channel.register(selector,SelectionKey.OP_READ);
							}
						}else if (key.isReadable()){
							System.out.println("isReadable:");
							int count = channel.read(buffer);
							if (count > 0) {
								total += count;
								
								msg = "";
								
								buffer.flip();
								while (buffer.remaining() > 0) {
									byte b = buffer.get();
									msg += (char)b;
								}
								
								System.out.println("msg:" + msg);
								buffer.clear();
							}
						}else {
							client.close();
							break _WHILE;
						}
					}
				}
				
				double last = System.currentTimeMillis();
				System.out.println(msg + ",used time : " + (last-start)/1000);
				msg = "";
			} catch (Exception e) {
				e.printStackTrace();
			}
				
		}
	}
	
	public static void main(String[] args){
		String names[] = new String[size];
		String url = null;
		if (null != args && args.length > 0) {
			url = args[0];
		}else {
//			url = "192.168.56.101";
			url = "localhost";
		}
		ip = new InetSocketAddress(url,8888);
		
		for (int i = 0 ; i < 1 ; i++) {
			names[i] = "aaa_" + i;
			
			new Thread(new Message(names[i])).start();
		}
	}
	
}
