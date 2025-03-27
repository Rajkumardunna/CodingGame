package com.game.codingGame;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.game.codingGame") 
public class CodingGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingGameApplication.class, args);
	}
}
