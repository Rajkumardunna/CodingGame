package com.game.codingGame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.codingGame.model.QuizQuestionRequest;
import com.game.codingGame.service.QuizQuestionService;

@RestController
@RequestMapping("/quiz")
public class QuizQuestionController {
	@Autowired
	private QuizQuestionService quizService;

	@PostMapping("/addQuestion")
	public String addQuestion(@RequestBody QuizQuestionRequest quizQuestion) {
		quizService.saveQuestion(quizQuestion);
		return "Question added successfully!";
	}
}
