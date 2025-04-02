package com.game.codingGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.codingGame.model.QuizQuestionRequest;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestionRequest, Long> {
}
