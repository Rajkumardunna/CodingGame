package com.game.codingGame.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.codingGame.model.DeleteQuestionRequest;
import com.game.codingGame.model.QuizQuestionRequest;
import com.game.codingGame.service.QuizQuestionService;

import jakarta.validation.Valid;

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
	
	@PostMapping("/deleteQuestion")
    public ResponseEntity<String> deleteQuestion(@Valid @RequestBody DeleteQuestionRequest deleteQuestion, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                                               .stream()
                                               .map(FieldError::getDefaultMessage)
                                               .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(String.join(", ", errors));
        }
        quizService.deleteQuestion(deleteQuestion.getQuestionId());
        String encodedQuestionId = StringEscapeUtils.escapeHtml4(deleteQuestion.getQuestionId().toString());
        return ResponseEntity.ok("Question ID#" + encodedQuestionId + " deleted successfully!");
    }
}
