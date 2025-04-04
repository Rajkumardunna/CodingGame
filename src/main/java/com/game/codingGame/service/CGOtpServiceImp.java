package com.game.codingGame.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
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
	// Expiry time for OTP in milliseconds (180 seconds = 180 * 1000 = 180000 ms)
	private static final long OTP_EXPIRY_TIME = 180 * 1000; // 3 Minutes
	private static final long CURRENT_TIME = System.currentTimeMillis();

	public String sendOTPEmail(CGRegistration cgRegistration) {
		String generatedOTP = generateOTP(cgRegistration);
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		String formattedTime = sdf.format(new Date(CURRENT_TIME)).toUpperCase();

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(cgRegistration.getEmail());
		message.setSubject("OTP for Email Verification | Coding Game");
		message.setText("Hello " + cgRegistration.getFirstName()+" "+ (cgRegistration.getLastName() == null ? "\b" : cgRegistration.getLastName())+ ",\n\n" +
				"Your OTP is " + generatedOTP + " for registration. Generated at " + formattedTime +" and valid for next 3 minutes.\n\n"+
				"From,\nTeam CG.");
		if (emailSender != null) {
			try {
				emailSender.send(message);
				System.out.println("Email sent successfully to " + cgRegistration.getEmail());
				cgRegistration.setOtp(generatedOTP);
			} catch (Exception e) {
				System.err.println("Error sending email: " + e.getMessage());
			}
		} else {
			System.err.println("EmailSender is not configured.");
		}
		return generatedOTP;
	}

	public String generateOTP(CGRegistration userRegistration) {
		int otpInt = (int)(Math.random() * 900000) + 100000;
		String otp = String.valueOf(otpInt);
		otpStore.put(userRegistration.getEmail(), new OTPDetails(otp, System.currentTimeMillis(), userRegistration));
		return otp;
	}

	public boolean validateOTP(String email, String otp) {
		OTPDetails otpDetails = otpStore.get(email);
		if (otpDetails == null) {
			return false;
		}
		if (CURRENT_TIME - otpDetails.getTimestamp() > OTP_EXPIRY_TIME) {
			otpStore.remove(email);
			return false;
		}
		return otpDetails.getOtp().equals(otp);
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
		@SuppressWarnings("unused")
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
