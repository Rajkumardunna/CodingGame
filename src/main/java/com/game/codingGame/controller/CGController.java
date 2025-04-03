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
import com.game.codingGame.service.CGOtpServiceImp;
import com.game.codingGame.service.CGService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/CodingGame")
public class CGController {

	@Autowired
	private CGService codingGameService;
	
	@Autowired 
	private CGOtpServiceImp otpService ;

	@PostMapping("/registration")
	public String registration(@RequestBody CGRegistration codingGameRegistration) {
	    final CGRegistration finalCodingGameRegistration = codingGameRegistration;
	    return Optional.ofNullable(otpService.sendOTPEmail(finalCodingGameRegistration))
	            .map(generateOTP -> {
	                final CGRegistration savedRegistration = codingGameService.saveUserRegistration(finalCodingGameRegistration);
	                String escapedFirstName = HtmlUtils.htmlEscape(savedRegistration.getFirstName());
	                String escapedLastName = HtmlUtils.htmlEscape(savedRegistration.getLastName());
	                return escapedFirstName + " " + escapedLastName + ", your OTP has been generated successfully and "
	                        + "sent to your registered Email. Valid for only 90 seconds.";
	            }).orElse("Something went wrong, please try again later.");
	}
	
	@PostMapping("/validateOTP")
	public String validateOpt(@RequestBody CGRegistration otpValidate, @RequestHeader("email") String email) {
		return codingGameService.validateOtp(otpValidate, email);
	}
	
	@PostMapping("/savePersonalDetails")
	public String savePersonalDetails(@RequestBody CGRegistration otpValidate, @RequestHeader("UserId") String userId) {
		String sanitizedUserId = HtmlUtils.htmlEscape(userId);
		return codingGameService.savePersonalDetails(otpValidate, sanitizedUserId);
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
