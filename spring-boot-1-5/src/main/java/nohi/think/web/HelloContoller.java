package nohi.think.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nohi on 2018/5/3.
 */
@RestController
@RequestMapping("/hello")
public class HelloContoller {

	@RequestMapping(value = "/{name}" )
	public String sayHello(@PathVariable(value = "name")String name){
		System.out.println("HelloContoller name:" + name);
		return "hello " + name;
	}

  @RequestMapping(value = "/json/{name}" )
  public Object respJson(@PathVariable(value = "name")String name){
	System.out.println("HelloContoller name:" + name);
	Map<String, String> resp = new HashMap<String, String>();
	resp.put( "OUT_CODE", name );
	resp.put( "OUT_MESG", "这是返回码，测试中文" );
	return resp;
  }
}
