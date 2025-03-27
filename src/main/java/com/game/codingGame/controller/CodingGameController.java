package com.game.codingGame.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.codingGame.loginSecurityConfig.LoginSecurityConfigEnAndDeCode;
import com.game.codingGame.modal.CodingGameRegitration;
import com.game.codingGame.service.CodingGameService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/CodingGame")
public class CodingGameController {

	@Autowired
	private CodingGameService codingGameService;
	
	//private LoginSecurityConfigEnAndDeCode loginSecurityConfigEnAndDeCode;

	@PostMapping("/registration")
	public String registration(@RequestBody CodingGameRegitration codingGameRegitration) {
		//System.out.println("===============codingGameRegitration===="+codingGameRegitration.getPassword());
	//	String encodedPassword = loginSecurityConfigEnAndDeCode.encryptPassword(codingGameRegitration.getPassword());
		//System.out.println("===============encodedPassword===="+encodedPassword);
     // codingGameRegitration.setPassword(encodedPassword);
		codingGameRegitration = codingGameService.saveUserRegistration(codingGameRegitration);
		return codingGameRegitration.getFirstName()+" "+codingGameRegitration.getLastName()+" Your registrtion process successfully Happy to Login...! Your User Id : "+codingGameRegitration.getUserId();
	}

	@GetMapping("/getAllUserDetails")
	public List<CodingGameRegitration> getAllUserDetail() {
		return codingGameService.getUserRegitrationDetail();
	}

	@GetMapping("/getUserDetailByUserId")
	public ResponseEntity<?> getUserDetailById(@RequestHeader("UserId") String userId) {
		Optional<CodingGameRegitration> registrationDetail = codingGameService.findByUserId(userId);
		if (registrationDetail.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with the provided registration Id = " + userId);
		}
		return ResponseEntity.status(HttpStatus.OK).body(registrationDetail.get());
	}
	
	  @PostMapping("/login") 
	  public String login(@RequestBody CodingGameRegitration codingGameRegitration) {
		  boolean isAuthenticated = codingGameService.loging(codingGameRegitration);
		  if (isAuthenticated) {
			  return "Login successful!"; 
		  } else {
			  return "Invalid username or password.";
		  } 
	  }
	 


	/*
	 * // Get User by ID
	 * 
	 * @GetMapping("/getDetailById") public Optional<CodingGameRegitration>
	 * getUserRegitrationDetailById(@RequestHeader("RegitrationDetailById") int id){
	 * System.out.println("=======>>>run the log======"+id); return
	 * codingGameService.findById(id); }
	 */
	
	// PUT method to update user information

	/*
	 * @PutMapping("/edit/{id}") public String updateUser(@PathVariable int
	 * id, @RequestBody CodingGameRegitration codingGameRegitration) { Field[]
	 * fields = codingGameRegitration.getClass().getDeclaredFields();
	 * System.out.println("Number of fields: " + fields.length); for (int i = 0; i
	 * <fields.length; i++) { CodingGameRegitration userRegitration = fields.get(i);
	 * if (userRegitration.getId().equals(id)) { codingGameRegitration.set(i,
	 * updatedUser); return "User updated successfully!"; } } return
	 * "User not found!"; }
	 */

	//method to delete a user by ID
	
	/*
	 * @DeleteMapping("/deleteUserDetailByUserId") public String
	 * deleteUserDetailByUserId(@RequestHeader("UserId") String userId){
	 * codingGameService.deleteUserByUserId(userId); return "User with userId " +
	 * userId + " has been deleted successfully!"; }
	 * 
	 * @PostMapping("/encodeBase64") public String encodeBase64(@RequestBody String
	 * data) { return loginSecurityConfigEnAndDeCode.encode(data); }
	 * 
	 * // Endpoint for Base64 decoding
	 * 
	 * @PostMapping("/decodeBase64") public String decodeBase64(@RequestBody String
	 * encodedData) { return loginSecurityConfigEnAndDeCode.decode(encodedData); }
	 */
	 
}
