package com.game.codingGame.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.game.codingGame.model.CGRegistration;
import com.game.codingGame.repository.CGRepository;

@Service
public class CGServiceImplement implements CGService{
	@Autowired
	public CGRepository codingGameRepository;

	public CGRegistration saveUserRegistration(CGRegistration codingGameRegistration) {
		
		codingGameRepository.save(codingGameRegistration);
		String userId = codingGameRegistration.getFirstName()+String.valueOf(codingGameRegistration.getSeqNum())+codingGameRegistration.getLastName().substring(0,1);
		codingGameRepository.updateUserBySeqNum(userId,codingGameRegistration.getSeqNum());
		codingGameRegistration.setUserId(userId);
		return codingGameRegistration;
	}

	@SuppressWarnings("unchecked")
	public List<CGRegistration> getUserRegistrationDetail() {
		if (codingGameRepository.findAll().isEmpty()) {
			return (List<CGRegistration>) ResponseEntity.status(HttpStatus.NOT_FOUND).body("Oops.....!");
		}
		return codingGameRepository.findAll();
	}

	@Override
	public Optional<CGRegistration> findByUserId(String userId) {
		return codingGameRepository.findByUserId(userId);
	}

	@Override
	public int deleteUserByUserId(String userId) {
		return codingGameRepository.deleteByUserId(userId);
	}

	@Override
	public boolean login(CGRegistration codingGameRegistration) {
		Optional<CGRegistration> userOptional = codingGameRepository.findByUserId(codingGameRegistration.getUserId());
		if (userOptional.isPresent()) {
			CGRegistration user = userOptional.get();
			return user.getPassword().equals(codingGameRegistration.getPassword());
		}
		return false;
	}
}
