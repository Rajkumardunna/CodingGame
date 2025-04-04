package com.game.codingGame.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import com.game.codingGame.model.CGRegistration;
import com.game.codingGame.model.SavePersonalDetailsRequest;

@Service
public interface CGService {
	@Autowired
	public String saveUserRegistration(CGRegistration cgRegistration);
	public String validateOtp(String emailOtp, String email);
	public String resendOtp(String email);
	public String savePersonalDetails(SavePersonalDetailsRequest savePersonalDetailsRequest, String email);
	public boolean login(SavePersonalDetailsRequest SavePersonalDetailsRequest);
	public List<CGRegistration> getUserRegistrationDetail();
	Optional<SavePersonalDetailsRequest> findByUserId(@Param("userId") String userId);
	int deleteUserByUserId(@Param("userId") String userId);
}