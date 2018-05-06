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
@RequestMapping("/a")
public class ThymeleafController {
	private Logger logger = LoggerFactory.getLogger( ThymeleafController.class);
	/**
	 * 测试hello
	 * @return
	 */
	@RequestMapping(value = "/abc",method = RequestMethod.GET)
	public String hello(Model model) {
		model.addAttribute("name", "Dear");
		System.out.println("====================");
		return "hello";
	}
}
