package nohi.think;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAdminServer
@EnableSwagger2
@ServletComponentScan
public class SpringBoot15Application {

//	@Bean
//	public RemoteIpFilter remoteIpFilter() {
//		return new RemoteIpFilter();
//	}

//	@Bean
//	public FilterRegistrationBean testFilterRegistration() {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		registration.setFilter(new XssFilter());//添加过滤器
//		registration.addUrlPatterns("/*");//设置过滤路径，/*所有路径
//		registration.addInitParameter("name", "alue");//添加默认参数
//		registration.setName("MyFilter");//设置优先级
//		registration.setOrder(1);//设置优先级
//		return registration;
//	}

	public static void main(String[] args) {
		System.getProperties().setProperty( "ext.a2","System.getProperties().a2" );
		SpringApplication.run(SpringBoot15Application.class, args);
	}
}
