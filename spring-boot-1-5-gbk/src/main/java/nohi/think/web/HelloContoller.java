package nohi.think.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nohi on 2018/5/3.
 */
@RestController
@RequestMapping("/hello")
public class HelloContoller {

  @RequestMapping(value = "/utf8/{name}" , produces = { "application/json;charset=UTF-8" })
  public String say1(@PathVariable(value = "name")String name){
	System.out.println("HelloContoller name:" + name);
	name = "中言";
	System.out.println("aa:" + name);
	return "hello " + name;
  }

	@RequestMapping(value = "/{name}" )
	public String sayHello(@PathVariable(value = "name")String name){
		System.out.println("HelloContoller name:" + name);
	  name = "中言";
	  System.out.println("aa:" + name);
		return "hello " + name;
	}

  @RequestMapping(value = "/json/{name}")
  public Object respJson(@PathVariable(value = "name")String name){
	System.out.println("HelloContoller name:" + name);
	Map<String, String> resp = new HashMap<String, String>();
	resp.put( "OUT_CODE", name );
	try {
	  String msg = "123这是中文456";
	  System.out.println("MSG:" + msg);
	  System.out.println("1 UTF-8:" + new String(msg.getBytes(),"UTF-8"));
	  System.out.println("2 GBK :" + new String(msg.getBytes(),"GBK"));
	  System.out.println("3 GBK - GBK:" + new String(msg.getBytes("GBK"),"GBK"));
	  System.out.println("4 GBK - UTF-8:" + new String(msg.getBytes("GBK"),"UTF-8"));
	  System.out.println("5 UTF-8 - GBK:" + new String(msg.getBytes("UTF-8"),"GBK"));
	  System.out.println("6:UTF-8 - UTF-8" + new String(msg.getBytes("UTF-8"),"UTF-8"));
	  resp.put( "OUT_MESG", new String("这是返回码，测试中文，这是GBK".getBytes("GBK"),"GBK"));
	} catch (UnsupportedEncodingException e) {
	  e.printStackTrace();
	}
	return resp;
  }

  @RequestMapping(value = "/json/utf8/{name}", produces = { "application/json;charset=UTF-8" })
  public Object respJson2(@PathVariable(value = "name")String name){
	System.out.println("HelloContoller name:" + name);
	Map<String, String> resp = new HashMap<String, String>();
	resp.put( "OUT_CODE", name );
	try {
	  String msg = "123这是中文456";
	  System.out.println("MSG:" + msg);
	  System.out.println("1 UTF-8:" + new String(msg.getBytes(),"UTF-8"));
	  System.out.println("2 GBK :" + new String(msg.getBytes(),"GBK"));
	  System.out.println("3 GBK - GBK:" + new String(msg.getBytes("GBK"),"GBK"));
	  System.out.println("4 GBK - UTF-8:" + new String(msg.getBytes("GBK"),"UTF-8"));
	  System.out.println("5 UTF-8 - GBK:" + new String(msg.getBytes("UTF-8"),"GBK"));
	  System.out.println("6:UTF-8 - UTF-8" + new String(msg.getBytes("UTF-8"),"UTF-8"));
	  resp.put( "OUT_MESG", new String("这是返回码，测试中文，这是GBK".getBytes("GBK"),"GBK"));
	} catch (UnsupportedEncodingException e) {
	  e.printStackTrace();
	}
	return resp;
  }
  @RequestMapping(value = "/json/gbk/{name}", produces = { "application/json;charset=GBK" })
  public Object respJson3(@PathVariable(value = "name")String name){
	System.out.println("HelloContoller name:" + name);
	Map<String, String> resp = new HashMap<String, String>();
	resp.put( "OUT_CODE", name );
	try {
	  String msg = "123这是中文456";
	  System.out.println("MSG:" + msg);
	  System.out.println("1 UTF-8:" + new String(msg.getBytes(),"UTF-8"));
	  System.out.println("2 GBK :" + new String(msg.getBytes(),"GBK"));
	  System.out.println("3 GBK - GBK:" + new String(msg.getBytes("GBK"),"GBK"));
	  System.out.println("4 GBK - UTF-8:" + new String(msg.getBytes("GBK"),"UTF-8"));
	  System.out.println("5 UTF-8 - GBK:" + new String(msg.getBytes("UTF-8"),"GBK"));
	  System.out.println("6:UTF-8 - UTF-8" + new String(msg.getBytes("UTF-8"),"UTF-8"));
	  resp.put( "OUT_MESG", new String("这是返回码，测试中文，这是GBK".getBytes("GBK"),"GBK"));
	} catch (UnsupportedEncodingException e) {
	  e.printStackTrace();
	}
	return resp;
  }

  @RequestMapping(value = "/json/gbk", produces = { "application/json;charset=GBK" })
  public Object reqrespJson(@RequestBody Demo demo, HttpServletRequest request){
	System.out.println("encoding:" + request.getCharacterEncoding() );
	System.out.println("name:" + request.getParameter( "name" ));
	System.out.println("demo gbk:\n" + JSONObject.toJSONString( demo ) );
	return demo;
  }

  @RequestMapping(value = "/json/utf8", produces = { "application/json;charset=UTF-8" })
  public Object reqrespJsonutf8(@RequestBody Demo demo){
	System.out.println("demo utf8:\n" + JSONObject.toJSONString( demo ) );
	return demo;
  }
}
