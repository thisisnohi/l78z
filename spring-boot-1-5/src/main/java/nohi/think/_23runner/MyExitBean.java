package nohi.think._23runner;

import javax.annotation.PreDestroy;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

/**
 * Created by nohi on 2018/5/4.
 */
@Component
public class MyExitBean implements ExitCodeGenerator {

	@PreDestroy
	public void exit(){
		System.out.println("============= exit ================");
	}

	@Override
	public int getExitCode() {
		System.out.println("========getExitCode========");
		return 111;
	}
}
