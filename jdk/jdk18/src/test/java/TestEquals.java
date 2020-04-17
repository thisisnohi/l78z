import org.junit.Test;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-03-25 20:22
 **/
public class TestEquals {
    @Test
    public void testE(){
        String a = "abc";
        String b = "abc";
        System.out.println("a.equals(b)?" + a.equals(b));
        System.out.println("a == b ?" + (a == b));
    }
}
