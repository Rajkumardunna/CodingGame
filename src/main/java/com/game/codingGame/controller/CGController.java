package com.game.codingGame.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.codingGame.model.CGRegistration;
import com.game.codingGame.service.CGService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/CodingGame")
public class CGController {

	@Autowired
	private CGService codingGameService;
	
	@PostMapping("/registration")
	public String registration(@RequestBody CGRegistration codingGameRegistration) {
		codingGameRegistration = codingGameService.saveUserRegistration(codingGameRegistration);
		String firstName = HtmlUtils.htmlEscape(codingGameRegistration.getFirstName());
		String lastName = HtmlUtils.htmlEscape(codingGameRegistration.getLastName());
		return firstName + " " + lastName + ", your registration has been processed successfully! We're happy to have you here. Your User Id : " + codingGameRegistration.getUserId();
	}

	@GetMapping("/getAllUserDetails")
	public List<CGRegistration> getAllUserDetail() {
		return codingGameService.getUserRegistrationDetail();
	}

	@GetMapping("/getUserDetailByUserId")
	public ResponseEntity<?> getUserDetailById(@RequestHeader("UserId") String userId) {
		Optional<CGRegistration> registrationDetail = codingGameService.findByUserId(userId);
		if (registrationDetail.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with the provided registration Id = " + userId);
		}
		return ResponseEntity.status(HttpStatus.OK).body(registrationDetail.get());
	}

	@PostMapping("/login") 
	public String login(@RequestBody CGRegistration codingGameRegistration) {
		boolean isAuthenticated = codingGameService.login(codingGameRegistration);
		if (isAuthenticated) {
			return "Login successful!"; 
		} else {
			return "Invalid username or password.";
		} 
	}
}
