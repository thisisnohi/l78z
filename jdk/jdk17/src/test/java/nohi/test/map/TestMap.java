package nohi.test.map;

import org.junit.Test;

import java.util.HashMap;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-08-30 20:42
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

    @Test
    public void testHash(){
        String key = "1";
        System.out.println("hash18:" + hash18(key));
        System.out.println("hash17:" + hash17(key));
    }

    static final int hash18(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    final int hash17(Object k) {
        int h = 0;
        if (0 != h && k instanceof String) {
            return sun.misc.Hashing.stringHash32((String) k);
        }

        h ^= k.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
}
