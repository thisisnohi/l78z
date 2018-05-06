package nohi.think.web;

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
		System.out.println("name:" + name);
		return "hello " + name;
	}
}
