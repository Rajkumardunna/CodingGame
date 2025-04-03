package com.game.codingGame.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.game.codingGame.model.QuizQuestionRequest;

import jakarta.transaction.Transactional;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestionRequest, Long> {
	
	@Modifying
	@Transactional
    @Query(value ="DELETE FROM QUIZ_QUESTION Q WHERE Q.ID = :id", nativeQuery = true)
	int deleteQuestionbById(String id);
	
}
