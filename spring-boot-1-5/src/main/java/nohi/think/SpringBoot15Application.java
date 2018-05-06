package nohi.think;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBoot15Application {

	public static void main(String[] args) {
		System.getProperties().setProperty( "ext.a2","System.getProperties().a2" );
		SpringApplication.run(SpringBoot15Application.class, args);
	}
}
