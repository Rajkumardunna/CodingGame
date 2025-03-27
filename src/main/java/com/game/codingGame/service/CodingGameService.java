package com.game.codingGame.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.game.codingGame.model.CodingGameRegistration;
@Service
public interface CodingGameService {
	@Autowired
	public CodingGameRegistration saveUserRegistration(CodingGameRegistration codingGameRegistration);
	public List<CodingGameRegistration> getUserRegistrationDetail();
	Optional<CodingGameRegistration> findByUserId(@Param("userId") String userId);
	int deleteUserByUserId(@Param("userId") String userId);
	public boolean loging(CodingGameRegistration codingGameRegistration);
}

