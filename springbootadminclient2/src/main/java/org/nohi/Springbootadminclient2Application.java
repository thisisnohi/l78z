package org.nohi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class Springbootadminclient2Application {

	@Configuration
	public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(Springbootadminclient2Application.class, args);
	}
}
