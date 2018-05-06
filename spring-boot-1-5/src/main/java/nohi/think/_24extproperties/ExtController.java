package nohi.think._24extproperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by nohi on 2018/5/5.
 */
@RestController
@RequestMapping("/ext")
public class ExtController {
	private Logger log = LoggerFactory.getLogger( ExtController.class);
	@Autowired
	private ExtProperties extP;

	@RequestMapping("")
	public Object getAllProperties(){
		log.debug( "ExtController:{}" , "getAllProperties" );
		log.info( "ExtController info:{}" , "getAllProperties" );
		log.error( "this is error" );
		System.out.println("=====getAllProperties======");
		return extP;
	}

}
