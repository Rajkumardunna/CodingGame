package com.game.codingGame.model;

import jakarta.validation.constraints.NotBlank;

public class DeleteQuestionRequest {
	
	@NotBlank(message = "Question ID cannot be blank")
	private String questionId;

	public String getQuestionId() {
		return questionId;
	}
	
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
}
