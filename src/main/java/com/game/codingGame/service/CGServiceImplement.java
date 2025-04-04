package com.game.codingGame.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.game.codingGame.model.CGRegistration;
import com.game.codingGame.model.SavePersonalDetailsRequest;
import com.game.codingGame.repository.CGRepository;
import com.game.constants.CGConstants;

@Service
public class CGServiceImplement implements CGService{
	
	@Autowired public CGRepository codingGameRepository;
	@Autowired public CGOtpServiceImp cGOtpServiceImp;

	@Override
	public String saveUserRegistration(CGRegistration cgRegistration) {
		int countEmail = codingGameRepository.countEmail(cgRegistration.getEmail());
		if(countEmail == 0) {
			codingGameRepository.save(cgRegistration);
			String userId = cgRegistration.getFirstName()+String.valueOf(cgRegistration.getSeqNum())+cgRegistration.getLastName().substring(0,1);
			codingGameRepository.updateUserBySeqNum(userId,cgRegistration.getSeqNum());
		}else {
			return "Email already exists! Please use a different Email address.";
		}
		String responseMessage = Optional.ofNullable(cGOtpServiceImp.sendOTPEmail(cgRegistration))
				.map(generateOTP -> {
					return HtmlUtils.htmlEscape(cgRegistration.getFirstName()) + " " + HtmlUtils.htmlEscape(cgRegistration.getLastName())
					+ ", your OTP has been generated successfully and sent to "+ cgRegistration.getEmail() +". Valid for 3 minutes.";
				})
				.orElse(CGConstants.RETRY_ERROR_MESSAGE);
		return responseMessage;
	}
	
	@Override
	public String validateOtp(String emailOtp, String email) {
		String updatOtpToNull = validOtp(emailOtp,email);
		if(updatOtpToNull.equals("OTP verified successfully!") || updatOtpToNull.equals("OTP expired.")) {
			codingGameRepository.updatebyOtp(email);
		}
		return updatOtpToNull;
	}
	
	public String validOtp(String emailOtp, String email) {
	    String mailAndDbOtp = codingGameRepository.findByOtpAndEmail(emailOtp, email);
	    return mailAndDbOtp != null && mailAndDbOtp.equals(emailOtp)
	        ? cGOtpServiceImp.validateOTP(email, emailOtp) ? "OTP verified successfully!" : "OTP expired." 
	        : "Invalid OTP";
	}
	
	@Override
	public String resendOtp(String email) {
		String[] firstAndLastName= codingGameRepository.findByEmailWithFirstName(email);
		CGRegistration cgRegistration = new CGRegistration(); 
		cgRegistration.setFirstName(firstAndLastName[0].replaceAll(",", " "));
		cgRegistration.setEmail(email);
		String responseMessage = Optional.ofNullable(cGOtpServiceImp.sendOTPEmail(cgRegistration))
				.map(generateOTP -> {
					codingGameRepository.updateResendOtp(cgRegistration.getOtp(),cgRegistration.getEmail());
					return cgRegistration.getFirstName() + ", your OTP has been generated successfully and "
					+ "sent to "+ cgRegistration.getEmail() +". Valid for only 3 minutes.";
				})
				.orElse(CGConstants.RETRY_ERROR_MESSAGE);
		return responseMessage;
	}
	
	@Override
	public String savePersonalDetails(SavePersonalDetailsRequest savePersonalDetailsRequest, String userId) {
	    int rowsUpdated = codingGameRepository.updatePersonaDetails(
	    		savePersonalDetailsRequest.getEducationQualification(), 
	    		savePersonalDetailsRequest.getProgrammingLanguage(), 
	    		savePersonalDetailsRequest.getGender(), 
	    		savePersonalDetailsRequest.getLocation(), 
	    		savePersonalDetailsRequest.getPassword(), 
	            userId);
	    return (rowsUpdated > 0) ? "Personal details are added successfully. Your UserId is "+userId+"" : "Failed to add personal details. Please try again.";
	}
	
	@Override
	public boolean login(SavePersonalDetailsRequest savePersonalDetailsRequest) {
		Optional<SavePersonalDetailsRequest> userOptional = codingGameRepository.findByUserId(savePersonalDetailsRequest.getUserId());
		if (userOptional.isPresent()) {
			SavePersonalDetailsRequest user = userOptional.get();
			return user.getPassword().equals(savePersonalDetailsRequest.getPassword());
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<CGRegistration> getUserRegistrationDetail() {
		if (codingGameRepository.findAll().isEmpty()) {
			return (List<CGRegistration>) ResponseEntity.status(HttpStatus.NOT_FOUND).body(CGConstants.RETRY_ERROR_MESSAGE);
		}
		return codingGameRepository.findAll();
	}

	@Override
	public Optional<SavePersonalDetailsRequest> findByUserId(String userId) {
		return codingGameRepository.findByUserId(userId);
	}

	@Override
	public int deleteUserByUserId(String userId) {
		return codingGameRepository.deleteByUserId(userId);
	}
}
