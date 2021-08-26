package com.cardinity.projecttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class ProjecttaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjecttaskApplication.class, args);
	}
}
