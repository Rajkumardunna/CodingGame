package com.game.codingGame.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.game.codingGame.modal.CodingGameRegistration;
import com.game.codingGame.repository.CodingGameRepository;

@Service
public class CodingGameServiceImplement implements CodingGameService{
	@Autowired
	public CodingGameRepository codingGameRepository;

	public CodingGameRegistration saveUserRegistration(CodingGameRegistration codingGameRegistration) {
		codingGameRepository.save(codingGameRegistration);
		String userId = codingGameRegistration.getFirstName()+String.valueOf(codingGameRegistration.getSeqNum())+codingGameRegistration.getLastName().substring(0,1);
		codingGameRepository.updateUserBySeqNum(userId,codingGameRegistration.getSeqNum());
		codingGameRegistration.setUserId(userId);
		return codingGameRegistration;
	}

	@SuppressWarnings("unchecked")
	public List<CodingGameRegistration> getUserRegistrationDetail() {
		if (codingGameRepository.findAll().isEmpty()) {
			return (List<CodingGameRegistration>) ResponseEntity.status(HttpStatus.NOT_FOUND).body("Oops.....!");
		}
		return codingGameRepository.findAll();
	}

	@Override
	public Optional<CodingGameRegistration> findByUserId(String userId) {
		return codingGameRepository.findByUserId(userId);
	}

	@Override
	public int deleteUserByUserId(String userId) {
		return codingGameRepository.deleteByUserId(userId);
	}

	@Override
	public boolean loging(CodingGameRegistration codingGameRegistration) {
		Optional<CodingGameRegistration> userOptional = codingGameRepository.findByUserId(codingGameRegistration.getUserId());
		if (userOptional.isPresent()) {
			CodingGameRegistration user = userOptional.get();
			return  user.getPassword().equals(codingGameRegistration.getPassword());
		}
		return false;
	}
}
