package com.game.codingGame.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.game.codingGame.modal.CodingGameRegitration;
@Service
public interface CodingGameService {
	@Autowired
	public CodingGameRegitration saveUserRegistration(CodingGameRegitration codingGameRegitration);
	public List<CodingGameRegitration> getUserRegitrationDetail();
	Optional<CodingGameRegitration> findByUserId(@Param("userId") String userId);
	int deleteUserByUserId(@Param("userId") String userId);
	public boolean loging(CodingGameRegitration codingGameRegitration);
}

