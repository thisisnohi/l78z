package nohi.test.map;

import org.junit.Test;

import java.util.HashMap;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-08-30 20:25
 **/
public class TestMap {

    @Test
    public void testMap(){
        HashMap<String, String> map = new HashMap<>();
        String result = map.put("1", "1111");
        System.out.println("result:" + result);
        result = map.put("1", "2222");
        System.out.println("result:" + result);
    }
}
