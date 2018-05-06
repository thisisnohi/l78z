package nohi.think._23runner;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by nohi on 2018/5/4.
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {
	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		System.out.println("===============MyApplicationRunner =====================");
		System.out.println( JSONObject.toJSONString( applicationArguments ));
	}
}
