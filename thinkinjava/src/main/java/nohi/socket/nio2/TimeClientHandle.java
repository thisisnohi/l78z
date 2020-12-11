package nohi.socket.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-09-24 16:43
 **/
public class TimeClientHandle implements Runnable{
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    //默认boolean值为false
    private volatile boolean stop;

    public TimeClientHandle(String host, int port) {
        //host若为空，则设置为指定ip
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            //打开多路复用器
            selector = Selector.open();
            //打开管道
            socketChannel = SocketChannel.open();
            //设置管道为非阻塞模式
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stop) {
            try {
                //阻塞等待1s，若超时则返回
                selector.select(1000);
                //获取所有selectionkey
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //遍历所有selectionkey
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    //获取之后删除
                    it.remove();
                    try {
                        //处理该selectionkey
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            //取消selectionkey
                            key.cancel();
                            if (key.channel() != null) {
                                //关闭该通道
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if (selector != null) {
            try {
                //关闭多路复用器
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleInput(SelectionKey key) throws IOException{
        //若该selectorkey可用
        if (key.isValid()) {
            //将key转型为SocketChannel
            SocketChannel sc = (SocketChannel) key.channel();
            //判断是否连接成功
            if (key.isConnectable()) {
                //若已经建立连接
                if (sc.finishConnect()) {
                    //向多路复用器注册可读事件
                    sc.register(selector, SelectionKey.OP_READ);
                    //向管道写数据
                    doWrite(sc);
                }else {
                    //连接失败 进程退出
                    System.exit(1);
                }
            }

            //若是可读的事件
            if (key.isReadable()) {
                //创建一个缓冲区
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                System.out.println("before  :  "+readBuffer);
                //从管道中读取数据然后写入缓冲区中
                int readBytes = sc.read(readBuffer);
                System.out.println("after :  "+readBuffer);
                //若有数据
                if (readBytes > 0) {
                    //反转缓冲区
                    readBuffer.flip();
                    System.out.println(readBuffer);

                    byte[] bytes = new byte[readBuffer.remaining()];
                    //获取缓冲区并写入字节数组中
                    readBuffer.get(bytes);
                    //将字节数组转换为String类型
                    String body = new String(bytes);
                    System.out.println(body.length());
                    System.out.println("Now is : " + body + "!");
                    this.stop = true;
                } else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                } else {
                    sc.register(selector, SelectionKey.OP_READ);
                }
            }
        }
    }
    public void doConnect() throws IOException {
        //通过ip和端口号连接到服务器
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            //向多路复用器注册可读事件
            socketChannel.register(selector, SelectionKey.OP_READ);
            //向管道写数据
            doWrite(socketChannel);
        } else {
            //若连接服务器失败,则向多路复用器注册连接事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }
    private void doWrite(SocketChannel sc) throws IOException {
        //要写的内容
        byte[] req = "    -    QUERY TIME ORDER     -   ".getBytes();
        //为字节缓冲区分配指定字节大小的容量
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        //将内容写入缓冲区
        writeBuffer.put(req);
        //反转缓冲区
        writeBuffer.flip();
        //输出打印缓冲区的可读大小
        System.out.println(writeBuffer.remaining());
        //将内容写入管道中
        sc.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            //若缓冲区中无可读字节，则说明成功发送给服务器消息
            System.out.println("Send order 2 server succeed.");
        }
    }

}
