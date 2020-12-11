package nohi.socket;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-09-24 15:29
 **/
public class NioSocketClient {
    private final static String TAG = "NioSocketClient";
    private final static int DATA_MAX_LEN = 0xFFFF;
    private String serverIp;
    private int port;
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private volatile boolean isShutDown = false;
    private CountDownLatch countDownLatch = new CountDownLatch(1);
    private SocketChannel socketChannel;
    private Selector selector;
    private final BlockingQueue<byte[]> sendDataQueue = new ArrayBlockingQueue<>(100);
    private final ByteBuffer sendDataBuffer = ByteBuffer.allocate(DATA_MAX_LEN);
    private NioSocketClient(String ip, int port){
        this.serverIp = ip;
        this.port = port;
    }

    public static NioSocketClient create(String serverIp,int port){
        return new NioSocketClient(serverIp,port);
    }

    /**
     * 发送任务处理线程
     * @param data byte[]
     */
    public void sendData(final byte[] data){
        if (data==null){
            return;
        }
        try{
            sendDataQueue.offer(data);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 1 启动连接及读数据监听线程；
     * 2 启动发数据监听线程；
     */
    public void start() {
        executor.execute(()->{
            try {
                System.out.println("====1111=====");
                countDownLatch.await();
                System.out.println("====1111  222=====");
                while (!isShutDown){
                    if (socketChannel!=null && socketChannel.isConnected()){
                        byte[] data =  sendDataQueue.take();
                        if (data!=null&&data.length>0){
                            sendDataBuffer.clear();
                            sendDataBuffer.put(data);
                            sendDataBuffer.flip();
                            socketChannel.write(sendDataBuffer);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //启动及接收数据线程
        executor.execute(()->{
            try{
                System.out.println("====22222=====");
                socketChannel = SocketChannel.open();
                System.out.println("====22222 111=====");
                socketChannel.configureBlocking(false);
                selector = Selector.open();
                SocketAddress socketAddress = new InetSocketAddress(serverIp, port);
                //连接服务端socket
                socketChannel.connect(socketAddress);
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
                //数据缓冲
                ByteBuffer dataBuffer = ByteBuffer.allocate(DATA_MAX_LEN);
                //selector处理
                while (!isShutDown) {
                    selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = keys.iterator();
                    SocketChannel client;
                    while(keyIterator.hasNext()){
                        SelectionKey key = keyIterator.next();
                        keyIterator.remove();
                        client = (SocketChannel)key.channel();
                        if (key.isConnectable()){
                            System.out.println("与服务端口["+serverIp+":"+port+"]连接成功");
                            if(client.finishConnect()){
                                client.configureBlocking(false);
                                client.register(selector, SelectionKey.OP_READ);
                                countDownLatch.countDown();
                            }
                        }else if (key.isReadable()){
                            System.out.println("key.isReadable()----");
                            dataBuffer.clear();
                            int readLen =client.read(dataBuffer);
                            byte[] result = new byte[readLen];
                            dataBuffer.flip();
                            dataBuffer.get(result);
                            System.out.println("result:" + new String(result));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 关闭该客户端
     */
    public void shutDown(){
        try{
            if (socketChannel!=null){
                socketChannel.close();
            }
            if (selector!=null){
                selector.close();
            }
        }catch ( Exception e){
            e.printStackTrace();
        }
        isShutDown = true;
        executor.shutdownNow();
    }
}
