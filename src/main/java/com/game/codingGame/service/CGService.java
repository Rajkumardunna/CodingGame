package com.game.codingGame.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.game.codingGame.model.CGRegistration;
@Service
public interface CGService {
	@Autowired
	public CGRegistration saveUserRegistration(CGRegistration codingGameRegistration);
	public List<CGRegistration> getUserRegistrationDetail();
	Optional<CGRegistration> findByUserId(@Param("userId") String userId);
	int deleteUserByUserId(@Param("userId") String userId);
	public boolean login(CGRegistration codingGameRegistration);
}
