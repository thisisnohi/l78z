package nohi.think.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by nohi on 2018/5/3.
 */
@Controller
@RequestMapping("/")
public class WelcomeController {
	private Logger log = LoggerFactory.getLogger( WelcomeController.class);
	/**
	 * 测试hello
	 * @return
	 */
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String hello(Model model) {
		log.debug( "WelcomeController:{}" , "hello" );
		model.addAttribute("name", "wellcome");
		System.out.println("========= this is welcome page===========");
		return "hello";
	}
}
