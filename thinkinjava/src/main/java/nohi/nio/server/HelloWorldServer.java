package nohi.nio.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;

import nohi.file.FileRead;

public class HelloWorldServer {
	static int BLOCK = 1024;
	static String name = "";
	static String charset = "UTF-8";
	protected Selector selector;
	protected ByteBuffer clientBuffer = ByteBuffer.allocate(BLOCK);
	static 	  CharsetDecoder decoder = Charset.forName(charset).newDecoder();
	static	  CharsetEncoder encoder = Charset.forName(charset).newEncoder();
	
	public HelloWorldServer(int port) throws IOException {
		selector = this.getSelector(port);
	}
	
	protected Selector getSelector(int port) throws IOException{
		ServerSocketChannel server = ServerSocketChannel.open();
		Selector sel = Selector.open();
		
		server.socket().bind(new InetSocketAddress(port));
		server.configureBlocking(false);
		server.register(sel, SelectionKey.OP_ACCEPT);
		
		return sel;
	}
	
	public void listen(){
		while(true){
			try {
			
				int count = selector.select();
				if (count > 0) {
					System.out.println("a new selector...");
					Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
					
					while (iter.hasNext()) {
						SelectionKey key = iter.next();
						iter.remove();
						if (key.isValid()) {
							process(key);
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void process(SelectionKey key) throws IOException{
		if (key.isAcceptable()) { //接收
			System.out.println("key is acceptable ");
			ServerSocketChannel server = (ServerSocketChannel) key.channel();
			SocketChannel channel = server.accept();
			//设置非阻塞模式	
			channel.configureBlocking(false);
			channel.register(selector, SelectionKey.OP_READ);
		}else if (key.isReadable()) { //读
			System.out.println("key is isReadable ");
			SocketChannel channel = (SocketChannel) key.channel();
			clientBuffer.clear();
			
			int count = channel.read(clientBuffer);
			System.out.println("count:" + count);
			
			if (count > 0 ) {
				clientBuffer.flip();
				CharBuffer charBuffer = decoder.decode(clientBuffer);
				
				name = charBuffer.toString();
//				name = clientBuffer.toString();
				
				System.out.println("name:" + name);
				
				SelectionKey skey = channel.register(selector, SelectionKey.OP_WRITE);
				skey.attach(name);
			}else {
				channel.close();
			}
			
			clientBuffer.clear();
		
		}else if (key.isWritable()) { //写
			System.out.println("key is isWritable ");
			SocketChannel channel = (SocketChannel) key.channel();
			String name = (String)key.attachment();
			
			FileRead fr = new FileRead();
			InputStream fis = fr.getResource("resp.txt");
			String msg = fr.readBuffer(fis);
			//System.out.println("aa\n" + msg);
//			ByteBuffer block = encoder.encode(CharBuffer.wrap(msg));
//			channel.write(block);

			byte[] reponseArray = msg.getBytes();
			int total = reponseArray.length;
			int size = 10240;
			double rs = (double)total / size;
			int times = (int)Math.ceil(rs);
			System.out.println("--------- writer start -------------");
			
			ByteBuffer block = encoder.encode(CharBuffer.wrap(msg));
			while (block.hasRemaining()) {
				channel.write(block);
			}
			
//			ByteBuffer bf = ByteBuffer.allocate(size);
//			for (int i = 0 ; i < times ; i++) {
//				int start = i * size;
//				int end = (i+1) * size;
//				
//				//如果超过总长度
//				byte[] b ;
//				if (end < total) {
//					b = new byte[size];
//				}else {
//					b = new byte[total - start];
//				}
//				System.arraycopy(reponseArray, start, b, 0, b.length);
//				bf.wrap(b);
//				channel.write(bf);
//			}
			
			System.out.println("--------- writer end -------------");
//			System.out.println("bb\n" + msg);
			key.cancel();
			channel.close();
		}
		
	}
	
	public static void main(String[] args){
		int port = 8888;
		try {
			HelloWorldServer server = new HelloWorldServer(port);
			System.out.println("listening on " + port);
			server.listen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
