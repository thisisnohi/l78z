package org.nohi;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class Springbootadmin2Application {

	public static void main(String[] args) {
		SpringApplication.run(Springbootadmin2Application.class, args);
	}
}
