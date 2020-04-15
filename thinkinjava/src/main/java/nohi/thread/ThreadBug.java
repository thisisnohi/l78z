package nohi.thread;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程问题: 一个不可见性的示例
 *
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-13 13:18
 **/
public class ThreadBug {
    private static int intVal1;
    private static  volatile  int intVal2;
    private static  volatile  boolean stop = false;
    private static AtomicInteger atInt;

    static class ReadThread implements Runnable{

        @SneakyThrows
        @Override
        public void run() {
            while (!stop) {
                System.out.println(String.format("Read intVal1:%s intVal2:%s atInt:%s", intVal1, intVal2, atInt.get()));
                Thread.sleep(1000);
            }
        }
    }

    static class WriterThread implements Runnable{
        @SneakyThrows
        @Override
        public void run() {
            for(int i = 0; i < 6; i ++){
                intVal1 = i;
                intVal2 = i;
                if (null == atInt) {
                    atInt = new AtomicInteger();
                } else {
                    atInt.addAndGet(1);
                }
                System.out.println(String.format("Writer intVal1:%s intVal2:%s atInt:%s", intVal1, intVal2, atInt.get()));
                Thread.sleep(1000);
            }

            stop = true;
        }
    }

    @Test
    public void testThread() throws InterruptedException {
        new Thread(new WriterThread()).start();
        new Thread(new ReadThread()).start();

        while (true) {
            Thread.sleep(1000);
        }
    }
}
