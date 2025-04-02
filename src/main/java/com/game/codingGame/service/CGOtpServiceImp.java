package com.game.codingGame.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.game.codingGame.model.CGRegistration;
@Service
public class CGOtpServiceImp {

	@Autowired
	private JavaMailSender emailSender;
	private Map<String, OTPDetails> otpStore = new HashMap<>();
	// Expiry time for OTP in milliseconds (90 seconds = 90 * 1000 = 90000 ms)
	private static final long OTP_EXPIRY_TIME = 120 * 1000; // 90 seconds

	public String sendOTPEmail(CGRegistration userRegistration) {
		String generatedOTP = generateOTP(userRegistration);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(userRegistration.getEmail());
		message.setSubject("OTP Code for Email Verification");
		message.setText("Hello " + userRegistration.getFirstName() + ",\n\nYour OTP is: " + generatedOTP);
		if (emailSender != null) {
			emailSender.send(message);
			System.out.println("Email sent successfully to: " + userRegistration.getEmail());
			userRegistration.setOtp(Integer.valueOf(generatedOTP));
		}	           
		return generatedOTP;
	}
	/*	public String generateOTP(CGRegistration userRegistration) {
		Random random = new Random();
		int otpInt = 100000 + random.nextInt(900000); 
		String otp = String.valueOf(otpInt);
		otpStore.put(userRegistration.getEmail(), new OTPDetails(otp, System.currentTimeMillis(), userRegistration));
		return otp;
	} */

	public String generateOTP(CGRegistration userRegistration) {
		int otpInt = (int)(Math.random() * 900000) + 100000;  // Generates a number between 100000 and 999999
		String otp = String.valueOf(otpInt);
		otpStore.put(userRegistration.getEmail(), new OTPDetails(otp, System.currentTimeMillis(), userRegistration));
		return otp;
	}

	/*public boolean validateOTP(String email, String inputOTP) {
		OTPDetails otpDetails = otpStore.get(email);
		if (otpDetails == null || System.currentTimeMillis() - otpDetails.getTimestamp() > TimeUnit.SECONDS.toMillis(60)) {
			return false;
		}
		return otpDetails.getOtp().equals(inputOTP);
	}*/
	public boolean validateOTP(String email, String otp) {
		OTPDetails otpDetails = otpStore.get(email);
		if (otpDetails == null) {
			return false;
		}
		long currentTime = System.currentTimeMillis();
		if (currentTime - otpDetails.getTimestamp() > OTP_EXPIRY_TIME) {
			otpStore.remove(email);
			return false;
		}
		return otpDetails.getOtp().equals(otp); // Check if the OTP matches
	}
	public void completeRegistration(String email) {
		OTPDetails otpDetails = otpStore.get(email);
		if (otpDetails != null) {
			CGRegistration userRegistration = otpDetails.getUserRegistration();
			System.out.println("Registration completed for " + userRegistration.getEmail());
			otpStore.remove(email); 
		}
	}
	private static class OTPDetails {
		private final String otp;
		private final long timestamp;
		private final CGRegistration userRegistration;

		public String getOtp() {
			return otp;
		}
		public long getTimestamp() {
			return timestamp;
		}
		public CGRegistration getUserRegistration() {
			return userRegistration;
		}
		public OTPDetails(String otp, long timestamp, CGRegistration userRegistration) {
			this.otp = otp;
			this.timestamp = timestamp;
			this.userRegistration = userRegistration;
		}
	}
}
