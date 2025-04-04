package com.game.codingGame.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.codingGame.model.CGRegistration;
import com.game.codingGame.model.OtpRequest;
import com.game.codingGame.model.SavePersonalDetailsRequest;
import com.game.codingGame.service.CGService;
import com.game.constants.CGConstants;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/CodingGame")
public class CGController {
	
	@Autowired private CGService codingGameService;
	
	@PostMapping("/registration")
	public ResponseEntity<String> registration(@Validated @RequestBody CGRegistration cgRegistration, BindingResult bindingResult) {
	    if (bindingResult.hasErrors()) {
	        String errorMessages = bindingResult.getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(", "));
	        return ResponseEntity.badRequest().body(errorMessages);
	    }
	    return ResponseEntity.ok(codingGameService.saveUserRegistration(cgRegistration));
	}
	
	@PostMapping("/validateOTP")
	public ResponseEntity<String> validateOtp(@RequestHeader("email") String emailId ,@Valid @RequestBody OtpRequest otpRequest,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errorMessages = bindingResult.getFieldErrors().stream().findFirst().map(error -> error.getDefaultMessage()).orElse(CGConstants.RETRY_ERROR_MESSAGE);
			return ResponseEntity.badRequest().body(errorMessages.toString());
		}
		return ResponseEntity.ok(codingGameService.validateOtp(otpRequest.getEmailOtp(), emailId));
	}

	@PostMapping("/resendOtp")
	public ResponseEntity<String> resendOtp(@RequestHeader("email") String emailId) {
		return ResponseEntity.ok(codingGameService.resendOtp(emailId));
	}
	
	@PostMapping("/savePersonalDetails")
	public ResponseEntity<String> savePersonalDetails( @RequestHeader("UserId") String userId, @Valid @RequestBody SavePersonalDetailsRequest savePersonalDetailsRequest, BindingResult bindingResult) {
		 if (bindingResult.hasErrors()) {
			 String errorMessages = bindingResult.getFieldErrors().stream().findFirst().map(error -> error.getDefaultMessage()).orElse(CGConstants.RETRY_ERROR_MESSAGE);
		        return ResponseEntity.badRequest().body(errorMessages.toString());
		    }
		return ResponseEntity.ok(codingGameService.savePersonalDetails(savePersonalDetailsRequest, HtmlUtils.htmlEscape(userId)));
	}

	@GetMapping("/getAllUserDetails")
	public List<CGRegistration> getAllUserDetail() {
		return codingGameService.getUserRegistrationDetail();
	}
	
	@GetMapping("/getUserDetailsByUserId")
	public ResponseEntity<?> getUserDetailsById(@RequestHeader("UserId") String userId) {
		Optional<SavePersonalDetailsRequest> registrationDetail = codingGameService.findByUserId(HtmlUtils.htmlEscape(userId));
		if (registrationDetail.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with the provided user Id " + userId);
		}
		return ResponseEntity.status(HttpStatus.OK).body(registrationDetail.get());
	}

	@PostMapping("/login") 
	public String login(@RequestBody SavePersonalDetailsRequest savePersonalDetailsRequest) {
		boolean isAuthenticated = codingGameService.login(savePersonalDetailsRequest);
		if (isAuthenticated) {
			return "Login successful!"; 
		} else {
			return "Invalid Username or Password.";
		} 
	}
}
