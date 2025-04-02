package com.game.codingGame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.game.codingGame.model.QuizQuestionRequest;

@Service
public interface QuizQuestionService {
	@Autowired
	public QuizQuestionRequest saveQuestion(QuizQuestionRequest quizQuestion);
}
