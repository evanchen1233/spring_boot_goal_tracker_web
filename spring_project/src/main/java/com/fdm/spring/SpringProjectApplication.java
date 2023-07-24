package com.fdm.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fdm.spring.controller.LoginController;

@SpringBootApplication
public class SpringProjectApplication {

	static Logger logger = LoggerFactory.getLogger(SpringProjectApplication.class);
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringProjectApplication.class, args);
		logger.info("Application started");
	}

}
