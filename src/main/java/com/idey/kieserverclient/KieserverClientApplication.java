package com.idey.kieserverclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class KieserverClientApplication extends org.springframework.boot.web.servlet.support.SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(KieserverClientApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(KieserverClientApplication.class);
	}
}
