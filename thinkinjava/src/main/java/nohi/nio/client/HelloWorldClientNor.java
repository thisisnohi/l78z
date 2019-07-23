package nohi.nio.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import nohi.file.FileRead;
import nohi.utils.CommonIntfUtils;

public class HelloWorldClientNor {
	
	private static String address = null;
	private static int port;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (null != args && args.length > 0) {
			address = args[0];
		}else {
			address = "192.168.56.101";
//			address = "localhost";
		}
		
		port = 8888;

		HelloWorldClientNor t = new HelloWorldClientNor();
		t.send();

	}

	public void send() {
		OutputStream os = null;
		InputStream is = null;

		FileRead fr = new FileRead();

		try {
			System.out.println("连接:" + address);
			Socket socket = new Socket(address, port);
			os = socket.getOutputStream();

			InputStream fis = fr.getResource("req.txt");

			String msg = fr.readBuffer(fis);

			System.out.println("请求:\n" + msg);
			System.out.println("长度:" + msg.getBytes().length);

			String head = CommonIntfUtils.getStringWithLength(msg.getBytes().length + "", 8);

			long start = System.currentTimeMillis();
			os.write((head + msg).getBytes());
			os.flush();
			socket.shutdownOutput();

			// Thread.sleep(10000);

			is = socket.getInputStream();
			String returnMsg = null;
			
			byte[] b = rcvBytesHead(socket,8);
			String lenStr = new String(b);
			System.out.println("接收报文，长度位：" + lenStr);
			int resBodyLength = Integer.parseInt(lenStr);
			System.out.println("resBodyLength:" + resBodyLength);
			byte[] msgByte = rcvBytes(socket,resBodyLength);
			returnMsg = new String(msgByte);
			
			long end = System.currentTimeMillis();
			System.out.println("msgByte:" + msgByte.length);
			System.out.println("耗时:" + (end-start)/1000);
//			System.out.println("响应:" + returnMsg);
			System.out.println("响应字符长度:" + returnMsg.length());
			System.out.println("响应:" + returnMsg.getBytes().length);
			System.out.println("响应(GBK):" + returnMsg.getBytes("GBK").length);
			System.out.println("响应(UTF-8):"+ returnMsg.getBytes("UTF-8").length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static byte[] rcvBytesHead(Socket socket, int length) throws IOException {
		InputStream in = socket.getInputStream();
		byte[] bs;
		int offset = 0;
		if (length <= 0) {
			int r = in.read();
			if (r == -1) {
				return new byte[0];
			}
			int t = in.available();
			if (t <= 0) {
				return new byte[] { (byte) r };
			}
			bs = new byte[t + 1];
			bs[0] = (byte) r;
			offset = 1;
		} else {
			bs = new byte[length];
		}
		int t = in.read(bs, offset, bs.length - offset);
		System.out.println("t:" + t + ",bs.legnth:" + bs.length);
		if (t < 0) {
			return new byte[0];
		}
		if (t == bs.length) {
			return bs;
		}
		byte[] rs = new byte[t + offset];
		System.arraycopy(bs, 0, rs, 0, t + offset);
		return rs;
	}
	public static byte[] rcvBytes(Socket socket, int length) throws IOException {
		InputStream in = socket.getInputStream();

		byte[] contentByte = new byte[length]; //消息体byte
		
		int n = 0 ; //读次数
		int index = 0;
		int count = 0;
		while (true){
			if (n++ > 1500000) {
				System.err.println("请求体循环超过100000次，退出!");
				break;
			}
			byte[] b = new byte[10240];
			int byteLen = in.read(b);
			if (byteLen >= 0 ) {
				count += byteLen;
				System.out.println("count:" + count + ",readLen:" + byteLen);
				
				for (int i = 0 ; i < byteLen;i++) {
					contentByte[index] = b[i];
					index ++;
				}
			}
			
			if (index >= length) {
				break;
			}
		}
		System.out.println("count:" + count + ",index:" + index + ",length:" + length);
		return contentByte;
	}
}
