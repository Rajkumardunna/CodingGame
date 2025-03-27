package com.game.codingGame.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.codingGame.loginSecurityConfig.LoginSecurityConfigEnAndDeCode;
import com.game.codingGame.modal.CodingGameRegistration;
import com.game.codingGame.service.CodingGameService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/CodingGame")
public class CodingGameController {

	@Autowired
	private CodingGameService codingGameService;
	
	@PostMapping("/registration")
	public String registration(@RequestBody CodingGameRegistration codingGameRegistration) {
		codingGameRegistration = codingGameService.saveUserRegistration(codingGameRegistration);
		return codingGameRegistration.getFirstName()+" "+codingGameRegistration.getLastName()+" Your registrtion process successfully Happy to Login...! Your User Id : "+codingGameRegistration.getUserId();
	}

	@GetMapping("/getAllUserDetails")
	public List<CodingGameRegistration> getAllUserDetail() {
		return codingGameService.getUserRegistrationDetail();
	}

	@GetMapping("/getUserDetailByUserId")
	public ResponseEntity<?> getUserDetailById(@RequestHeader("UserId") String userId) {
		Optional<CodingGameRegistration> registrationDetail = codingGameService.findByUserId(userId);
		if (registrationDetail.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with the provided registration Id = " + userId);
		}
		return ResponseEntity.status(HttpStatus.OK).body(registrationDetail.get());
	}

	@PostMapping("/login") 
	public String login(@RequestBody CodingGameRegistration codingGameRegistration) {
		boolean isAuthenticated = codingGameService.loging(codingGameRegistration);
		if (isAuthenticated) {
			return "Login successful!"; 
		} else {
			return "Invalid username or password.";
		} 
	}
}
