package com.game.codingGame.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.game.codingGame.loginSecurityConfig.LoginSecurityConfigEnAndDeCode;
import com.game.codingGame.modal.CodingGameRegitration;
import com.game.codingGame.repository.CodingGameRepository;

@Service
public class CodingGameServiceImplement implements CodingGameService{
	@Autowired
	public CodingGameRepository codingGameRepository;
	 @Autowired
	private LoginSecurityConfigEnAndDeCode loginSecurityConfigEnAndDeCode;

	public CodingGameRegitration saveUserRegistration(CodingGameRegitration codingGameRegitration) {
		codingGameRepository.save(codingGameRegitration);
		String userId = codingGameRegitration.getFirstName()+String.valueOf(codingGameRegitration.getSeqNum())+codingGameRegitration.getLastName().substring(0,1);
		System.out.println(codingGameRegitration.getPassword()+"--------------user name====>>"+userId);
		codingGameRepository.updateUserBySeqNum(userId,codingGameRegitration.getSeqNum());
		codingGameRegitration.setUserId(userId);
		return codingGameRegitration;
	}

	@SuppressWarnings("unchecked")
	public List<CodingGameRegitration> getUserRegitrationDetail() {
		if (codingGameRepository.findAll().isEmpty()) {
			return (List<CodingGameRegitration>) ResponseEntity.status(HttpStatus.NOT_FOUND).body("Oops.....!");
		}
		return codingGameRepository.findAll();
	}

	@Override
	public Optional<CodingGameRegitration> findByUserId(String userId) {
		return codingGameRepository.findByUserId(userId);
	}

	@Override
	public int deleteUserByUserId(String userId) {
		return codingGameRepository.deleteByUserId(userId);
	}

	@Override
	public boolean loging(CodingGameRegitration codingGameRegitration) {
		Optional<CodingGameRegitration> userOptional = codingGameRepository.findByUserId(codingGameRegitration.getUserId());
		if (userOptional.isPresent()) {
			CodingGameRegitration user = userOptional.get();
			// String encodedPassword = loginSecurityConfigEnAndDeCode.decode(user.getPassword());
			return  user.getPassword().equals(codingGameRegitration.getPassword());
		}
		return false;
	}
}
