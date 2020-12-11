package nohi.socket.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-09-24 16:42
 **/
public class MultiplexerTimeServer implements Runnable {
    private Selector selector;
    private ServerSocketChannel servChannel;
    private volatile boolean stop;

    //初始化多路复用器，绑定监听端口
    public MultiplexerTimeServer(int port) {
        try {
            //打开多路复用器
            selector = Selector.open();
            //打开服务器通道
            servChannel = ServerSocketChannel.open();
            //设置服务器通道为非阻塞模式
            servChannel.configureBlocking(false);
            //绑定端口,backlog指队列的容量，提供了容量限制的功能，避免太多客户端占用太多服务器资源
            //serverSocketChannel有一个队列，存放没有来得及处理的客户端,服务器每次accept，就会从队列中去一个元素。
            servChannel.socket().bind(new InetSocketAddress(port), 1024);
            //把服务器通道注册到多路复用器上，并监听阻塞事件
            servChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The server is start in port: " + port);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                //多路复用器开始工作（轮询），选择已就绪的通道
                //等待某个通道准备就绪时最多阻塞1秒，若超时则返回。
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (IOException e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会自动去注册并关闭
        //所以不需要重复释放资源
        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                //获取服务器通道
                ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
                //执行阻塞方法(等待客户端资源)
                SocketChannel sc = ssc.accept();
                //设置为非阻塞模式
                sc.configureBlocking(false);
                //注册到多路复用器上，并设置为可读状态
                sc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                //读取数据
                SocketChannel sc = (SocketChannel)key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);
                if (readBytes > 0) {
                    //反转缓冲区（复位）
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    //接受缓冲区数据
                    readBuffer.get(bytes);
                    //trim方法返回字符串的副本，忽略前导空白和尾部空白
                    String body = new String(bytes).trim();
//                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order : " + body);
//                    String currentTime = "QUERY TIME ORDER"
//                            .equalsIgnoreCase(body) ? new java.util.Date
//                            (System.currentTimeMillis()).toString()
//                            : "BAD ORDER";
                    String currentTime = new Date(System.currentTimeMillis()).toString();
                    //给客户端回写数据
                    doWrite(sc, currentTime);
                } else if (readBytes < 0) {
                    //对端链路关闭
                    key.cancel();
                    sc.close();
                }

            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException{
        if (response != null && response.trim().length() > 0) {
            System.out.println(response);
            byte[] bytes = response.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }


}
