package nohi.test.path;

import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author NOHI
 * @program: nohi-think
 * @description:
 * @create 2020-03-25 21:51
 **/
public class TestPath {
    @Test
    public void testPath(){
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        Assert.assertNotNull(is);
        System.out.println("is not null");
        String path = TestPath.class.getResource("application.properties").getPath();
        System.out.println("path:" + path);
        is = TestPath.class.getResourceAsStream("application.properties");
        Assert.assertNotNull(is);
    }
}
