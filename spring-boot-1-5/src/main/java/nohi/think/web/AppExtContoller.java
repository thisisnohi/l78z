package nohi.think.web;

import nohi.think._23runner.MyExitBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by nohi on 2018/5/3.
 */
@Controller
@RequestMapping("/")
public class AppExtContoller extends ApplicationObjectSupport {

	@Autowired
	private MyExitBean exit;

	@RequestMapping(value = "exit" )
	public String sayHello(){
		System.out.println("app will exit");

		SpringApplication.exit(super.getApplicationContext(), exit);

		return "exit " ;
	}
}
