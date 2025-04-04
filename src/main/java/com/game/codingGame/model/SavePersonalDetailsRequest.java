package com.game.codingGame.model;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Table(name = "CG_REGISTRATION_MASTER")
public class SavePersonalDetailsRequest {

	private String userId;

	@NotBlank(message = "Education Qualification cannot be null")
	private String educationQualification;

	@NotBlank(message = "Programming language cannot be null")
	private String programmingLanguage;

	@NotBlank(message = "Gender cannot be null")
	private String gender;

	@NotBlank(message = "Location cannot be null")
	@Pattern(regexp = "^[A-Za-z ]+$", message = "First name must contain only alphabets")
	private String location;

	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@$!%*?&) ")
	private String password;

	@NotBlank(message = "Confirm Password is required")
	@Size(min = 8, message = "Confirm Password must be at least 8 characters long")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Confirm Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@$!%*?&) ")
	private String confirmPassword;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEducationQualification() {
		return educationQualification;
	}

	public void setEducationQualification(String educationQualification) {
		this.educationQualification = educationQualification;
	}

	public String getProgrammingLanguage() {
		return programmingLanguage;
	}

	public void setProgrammingLanguage(String programmingLanguage) {
		this.programmingLanguage = programmingLanguage;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
