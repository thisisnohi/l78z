package nohi.test.socket;

import nohi.socket.NioSocketClient;
import nohi.socket.SocketServer;
import org.apache.commons.net.SocketClient;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-09-24 15:14
 **/
public class TestScoket {
    int port = 8081;

    @Test
    public void startServer(){

        SocketServer server = new SocketServer();
        server.startSocketServer(port);

        System.out.println("端口：" + port + "开启完成");
    }

    @Test
    public void testClient1(){
        NioSocketClient client = NioSocketClient.create("127.0.0.1", port);
        client.start();
        client.sendData(("current time : " + System.currentTimeMillis()).getBytes());

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testClient(){
        try (SocketChannel socketChannel = SocketChannel.open()) {
            //连接服务端socket
            SocketAddress socketAddress = new InetSocketAddress("localhost", 8080);
            socketChannel.connect(socketAddress);

            int sendCount = 0;

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //这里最好使用selector处理   这里只是为了写的简单
            while (sendCount < 10) {
                buffer.clear();
                //向服务端发送消息
                buffer.put(("current time : " + System.currentTimeMillis()).getBytes());
                //读取模式
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();

                //从服务端读取消息
                int readLenth = socketChannel.read(buffer);
                System.out.println("readLenth:" + readLenth);
                //读取模式
                buffer.flip();
                byte[] bytes = new byte[readLenth];
                buffer.get(bytes);
                System.out.println(new String(bytes, "UTF-8"));
                buffer.clear();

                sendCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
