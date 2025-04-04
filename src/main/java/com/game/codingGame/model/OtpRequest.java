package com.game.codingGame.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class OtpRequest {

	@NotBlank(message = "OTP cannot be null or empty")
	@Pattern(regexp = "^[0-9]{6}$", message = "OTP must be a 6-digit number")
	private String emailOtp;

	public String getEmailOtp() {
		return emailOtp;
	}
	public void setEmailOtp(String emailOtp) {
		this.emailOtp = emailOtp;
	}
	public static void main(String[] args) {
		
	}
	
}
