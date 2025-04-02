package com.game.codingGame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = "com.game.codingGame")
public class CGApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(CGApplication.class);
	private static final String GREEN = "\u001B[32m";
	private static final String RESET = "\u001B[0m";

	public static void main(String[] args) {
		SpringApplication.run(CGApplication.class, args);
		System.out.println(GREEN+"Application has started successfully!"+RESET);
		logger.info(GREEN+"Application has started successfully!"+RESET);
	}
}
