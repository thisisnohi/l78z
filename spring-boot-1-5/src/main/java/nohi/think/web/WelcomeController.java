package nohi.think.web;

import nohi.think._29jpa.entity.TUserEntity;
import nohi.think._29jpa.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private UserRepository userRepository;
	/**
	 * 测试hello
	 * @return
	 */
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String index(Model model) {
		log.debug( "WelcomeController:{}" , "hello" );
		model.addAttribute("name", "nohi");
		model.addAttribute("count1", 1);
		model.addAttribute("count2", 2);
		System.out.println("========= this is welcome page===========");

		Iterable<TUserEntity> it = userRepository.findAll();
		model.addAttribute("userList", it);
		return "index";
	}

	@RequestMapping(value = "/hello",method = RequestMethod.GET)
	public String hello(Model model) {
		log.debug( "WelcomeController:{}" , "hello" );
		model.addAttribute("name", "hello");

		model.addAttribute("count1", 1);
		model.addAttribute("count2", 2);
		System.out.println("========= this is welcome page===========");

		Iterable<TUserEntity> it = userRepository.findAll();
		model.addAttribute("userList", it);

		return "hello";
	}


	@RequestMapping(value = "/seats",method = RequestMethod.GET)
	public String seats(Model model) {
		return "seats";
	}

	@RequestMapping(value = "/seats2",method = RequestMethod.GET)
	public String seats2(Model model) {
		return "seats2";
	}
}
