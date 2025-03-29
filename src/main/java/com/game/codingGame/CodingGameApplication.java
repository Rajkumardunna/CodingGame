package com.game.codingGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = "com.game.codingGame") 
public class CodingGameApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(CodingGameApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CodingGameApplication.class, args);
		logger.info("Application has started successfully!");
	}
}
