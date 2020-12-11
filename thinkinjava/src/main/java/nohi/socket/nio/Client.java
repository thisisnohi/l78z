package nohi.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-09-24 15:59
 **/
public class Client {
    public static void main(String[] args) throws IOException {

        // 1. 连接到服务端
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));

        socketChannel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put("你好".getBytes());
        buffer.flip();
        // 2. 把 buffer 里面的数据通过 channel 发送出去
        socketChannel.write(buffer);
        buffer.clear();

        socketChannel.close();

        System.out.println("=======");
    }
}
