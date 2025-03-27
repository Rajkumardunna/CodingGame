package com.game.codingGame.loginSecurityConfig;
import java.util.Base64;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginSecurityConfigEnAndDeCode {

	public String encode(String data) {
		return Base64.getEncoder().encodeToString(data.getBytes());
	}
	public String decode(String encodedData) {
		byte[] decodedBytes = Base64.getDecoder().decode(encodedData);
		return new String(decodedBytes);
	}
}
