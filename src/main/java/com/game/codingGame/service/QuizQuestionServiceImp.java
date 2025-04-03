package com.game.codingGame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.game.codingGame.model.QuizQuestionRequest;
import com.game.codingGame.repository.QuizQuestionRepository;

@Service
public class QuizQuestionServiceImp implements QuizQuestionService {
	@Autowired
	private QuizQuestionRepository quizRepository;

	public QuizQuestionRequest saveQuestion(QuizQuestionRequest quizQuestion) {
		return quizRepository.save(quizQuestion);
	}
	
	@Override
	public int deleteQuestion(String questionId) {
		return quizRepository.deleteQuestionbById(questionId);
	}
	
}
