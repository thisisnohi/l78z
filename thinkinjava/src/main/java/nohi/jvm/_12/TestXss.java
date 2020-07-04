package nohi.jvm._12;

import java.util.ArrayList;
import java.util.List;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-04-25 08:19
 **/
public class TestXss {
    static int i = 0;

    public void loop(){
        i++;
        loop();
    }
    public static void main(String[] args){
        List list = new ArrayList<>();
        TestXss t = new TestXss();
        try {
            t.loop();
            System.out.println("===over=====");
        } catch(Throwable e) {
            System.out.println("===over=====i:" + i);
        }
    }
}
