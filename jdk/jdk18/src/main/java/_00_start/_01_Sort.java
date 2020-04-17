package _00_start;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-03-24 21:49
 **/
public class _01_Sort {

    @Test
    public void testSort(){
        List<String> names1 = Lists.newArrayList();
        names1.add("Google ");
        names1.add("Runoob ");
        names1.add("Taobao ");
        names1.add("Baidu ");
        names1.add("Sina ");
        names1.add(null);

        System.out.println("names1:" + names1);

//        Collections.sort(names1);
        System.out.println("default:" + names1);

        Collections.sort(names1, (a,b) -> a == null ? 1 : b == null ? -1 : b.compareTo(a));
        System.out.println("comparable:" + names1);

    }

    @Test
    public void testThread() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("====");
            }
        }).start();

        new Thread(() -> {System.out.println("11111");
            System.out.println("222");}).start();

        Thread.sleep(1000);
    }
}
