package com.game.codingGame.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.game.codingGame.model.CGRegistration;
import com.game.codingGame.repository.CGRepository;

@Service
public class CGServiceImplement implements CGService{
	@Autowired
	public CGRepository codingGameRepository;
	
	@Autowired
	public CGOtpServiceImp cgOtpServiceImp;

	public CGRegistration saveUserRegistration(CGRegistration codingGameRegistration) {
		codingGameRepository.save(codingGameRegistration);
		String userId = codingGameRegistration.getFirstName()+String.valueOf(codingGameRegistration.getSeqNum())+codingGameRegistration.getLastName().substring(0,1);
		codingGameRepository.updateUserBySeqNum(userId,codingGameRegistration.getSeqNum());
		codingGameRegistration.setUserId(userId);
		return codingGameRegistration;
	}

	@SuppressWarnings("unchecked")
	public List<CGRegistration> getUserRegistrationDetail() {
		if (codingGameRepository.findAll().isEmpty()) {
			return (List<CGRegistration>) ResponseEntity.status(HttpStatus.NOT_FOUND).body("Oops.....!");
		}
		return codingGameRepository.findAll();
	}

	@Override
	public Optional<CGRegistration> findByUserId(String userId) {
		return codingGameRepository.findByUserId(userId);
	}

	@Override
	public int deleteUserByUserId(String userId) {
		return codingGameRepository.deleteByUserId(userId);
	}

	@Override
	public boolean login(CGRegistration codingGameRegistration) {
		Optional<CGRegistration> userOptional = codingGameRepository.findByUserId(codingGameRegistration.getUserId());
		if (userOptional.isPresent()) {
			CGRegistration user = userOptional.get();
			return user.getPassword().equals(codingGameRegistration.getPassword());
		}
		return false;
	}

	@Override
	public String validateOtp(CGRegistration codingGameRegistration, String email) {
		Optional<CGRegistration> userOptional = codingGameRepository.findByOtpAndEmail(codingGameRegistration.getOtp(), email);
		return userOptional.map(user -> {
					System.out.println("Stored OTP for user: " + user.getOtp());
					if (user.getOtp() == codingGameRegistration.getOtp()) {
						boolean isOtpValid = cgOtpServiceImp.validateOTP(email, String.valueOf(codingGameRegistration.getOtp()));
						System.out.println("OTP Validation Result: " + isOtpValid);
						return isOtpValid ? "OTPCODE#01" : "OTPCODE#02";
					}else {
						return "Invalid OTP";
					}
				}).map(otpVerificationCode -> {
					switch (otpVerificationCode) {
					case "OTPCODE#01":
						return "OTP verified successfully!";
					case "OTPCODE#02":
						return "Request timed out..";
					default:
						return "Invalid OTP";
					}
				}).orElse("Invalid OTP");
	}

	@Override
	public String savePersonalDetails(CGRegistration userRegistration, String userId) {
	    int rowsUpdated = codingGameRepository.updatePersonaDetails(
	    		userRegistration.getEducationQualification(), 
	    		userRegistration.getProgrammingLanguage(), 
	    		userRegistration.getGender(), 
	    		userRegistration.getLocation(), 
	    		userRegistration.getPassword(), 
	            userId);
	    return (rowsUpdated > 0) ? "Personal details updated successfully. Your UserId is "+userId+"" : "Failed to update personal details. Please try again.";
	}
}
