package nohi.test.socket;

import nohi.socket.nio2.MultiplexerTimeServer;
import nohi.socket.nio2.TimeClientHandle;
import org.junit.Test;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-09-24 15:14
 **/
public class TestNioScoket {
    int port = 8080;
    @Test
    public void testNio2Server() throws InterruptedException {
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();

        Thread.sleep(30 * 1000);
    }

    @Test
    public void testNio2Client() throws InterruptedException {
        new Thread(new TimeClientHandle("127.0.0.1", port), "Time-Client-001").start();

        Thread.sleep(30 * 1000);
    }
}
