package nohi.test.json;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import nohi.json.Body;
import nohi.json.Head;
import nohi.json.Root;
import org.junit.Test;

/**
 * Created by nohi on 2019-6-14.
 */
public class TestJson {

  @Test
  public void test1(){
	Root<Head, Body> root  = new Root<Head, Body>();
	root.setCode( "c1" );
	root.setMsg( " this is msg " );

	Head head = new Head();
	head.setA( "aaa" );
	head.setB( "bbb" );
	root.setH( head );

	Body body = new Body();
	body.setPhone( "131111112222" );
	body.setUserName( "这是中文" );
	root.setB( body );

	String msg = JSONObject.toJSONString( root );
	System.out.println("msg:\n" + msg);

	root = JSONObject.parseObject(msg,new TypeReference<Root<Head, Body>>(){});

	System.out.println("root:" + root);
	System.out.println("h:" + root.getH());
	System.out.println("h:" + root.getH().getA());

	System.out.println("b:" + root.getB());
	System.out.println("h:" + root.getB().getUserName());

  }
}
