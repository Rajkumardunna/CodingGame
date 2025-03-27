package com.game.codingGame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.codingGame.modal.CodingGameRegitration;
import com.game.codingGame.repository.CodingGameRepository;

@Service
public class CodingGameServiceClass {

	private CodingGameRepository codingGameRepository;
	@Autowired
	public void CodingGameService(CodingGameRepository	 codingGameRepository) {
		this.codingGameRepository = codingGameRepository;
	}
	public CodingGameRegitration saveUserRegistration(CodingGameRegitration codingGameRegitration) {
		return codingGameRegitration;
	}
	public List<CodingGameRegitration> getUserRegitrationDetail() {
		return codingGameRepository.findAll();
	}
	public Optional<CodingGameRegitration> findById(int id) {
		return codingGameRepository.findById(id);
	}

}
