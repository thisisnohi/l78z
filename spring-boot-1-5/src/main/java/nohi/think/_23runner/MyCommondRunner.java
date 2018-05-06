package nohi.think._23runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by nohi on 2018/5/4.
 */
@Component
@Order(value = 1)
public class MyCommondRunner implements CommandLineRunner {
	@Override
	public void run(String... strings) throws Exception {
		System.out.println("===============MyCommondRunner =====================");
		System.out.println( strings);
	}
}
