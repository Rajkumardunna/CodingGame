package com.game.codingGame.loginSecurityConfig;
import java.util.Base64;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginSecurityConfigEnAndDeCode {
	
	/*
	 * // Method to hash the password public String encryptPassword(String password)
	 * { BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); return
	 * passwordEncoder.encode(password); // This hashes the password }
	 * 
	 * // Method to check if a raw password matches the encoded one public static
	 * boolean checkPassword(String rawPassword, String encodedPassword) {
	 * BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); return
	 * passwordEncoder.matches(rawPassword, encodedPassword); // This compares
	 * passwords }
	 */
	
	
    public String encode(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }
    public String decode(String encodedData) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedData);
        return new String(decodedBytes);
    }

    

}
