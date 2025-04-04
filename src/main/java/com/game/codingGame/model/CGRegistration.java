package com.game.codingGame.model;

 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "CG_REGISTRATION_MASTER")
public class CGRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int seqNum;
	private String userId;
	
	@NotBlank(message = "First name cannot be null or empty")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contains alphabets only")
	private String firstName;
	
	@Pattern(regexp = "^[A-Za-z]*$", message = "Middle name must contains alphabets only")
	private String middleName;
	
	@NotNull(message = "Last name cannot be null or empty")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contains alphabets only")
	private String lastName;
	
	@NotNull(message = "Phone number cannot be null or empty")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number must be 10 digits")
	private String phoneNumber;
	
	@NotNull(message = "Email cannot be null or empty")
	@Email(message = "Invalid email address")
	@Column(unique = true)
	private String email;

	private String otp;

	public int getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
}
