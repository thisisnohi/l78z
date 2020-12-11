package nohi.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {

    public static void main(String[] args) throws IOException {
        // 1. 创建服务端监听 channel 并配置为非阻塞
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        // 2. 配置 channel 监听的套接字
        serverSocketChannel.bind(new InetSocketAddress(8080));

        // 3. 创建选择器并把通道注册到 Selector 上，并注册感兴趣的事件
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 4. 在死循环中不断监听
        while (true) {
            if (selector.select() == 0) {
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 取消选择键
                iterator.remove();
                // 5. 处理 accept 就绪事件
                if (key.isAcceptable()) {
                    System.out.println("=== 服务端收到 accept 事件 ===");
                    // 5.1 获取来自客户端的连接，创建 SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 5.2 把该 Channel 注册到 selector 上（也就是理论上的把多个客户端的channel注册到selector上，用一个线程轮询）
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }

                // 6. 处理 selector 上的 read 事件
                if (key.isReadable()) {
                    // 6.1 建立与 client 的通信链路
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len = -1;
                    // 6.2 把 client 发送过来的数据读到 buffer 里面，并输出
                    while ((len = channel.read(byteBuffer)) != -1) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }
                }
            }
        }
    }
}
