package nohi;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

/**
 * Created by nohi on 2019-5-31.
 */
public class TestCharset {

  @Test
  public void test1() throws UnsupportedEncodingException {
	String msg = "123这是中文456";
	System.out.println("MSG:" + msg);
	System.out.println("1:" + new String(msg.getBytes(),"UTF-8"));
	System.out.println("2:" + new String(msg.getBytes(),"GBK"));
	System.out.println("3:" + new String(msg.getBytes("GBK"),"GBK"));
	System.out.println("4:" + new String(msg.getBytes("GBK"),"UTF-8"));
	System.out.println("5:" + new String(msg.getBytes("UTF-8"),"GBK"));
	System.out.println("6:" + new String(msg.getBytes("UTF-8"),"UTF-8"));
  }
}
