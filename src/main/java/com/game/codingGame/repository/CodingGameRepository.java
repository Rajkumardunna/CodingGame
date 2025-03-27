package com.game.codingGame.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.game.codingGame.modal.CodingGameRegistration;

import jakarta.transaction.Transactional;
@Repository
public interface CodingGameRepository extends JpaRepository<CodingGameRegistration, Integer>{
	
	@Query(value = "SELECT * FROM CG_REGISTRATION_MASTER WHERE USER_ID = :userId", nativeQuery = true)
	Optional<CodingGameRegistration> findByUserId(String userId);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE CG_REGISTRATION_MASTER C SET C.USER_ID = :userId WHERE C.SEQ_NUM = :seqNum", nativeQuery = true)
	int updateUserBySeqNum(String userId, int seqNum);
	
	@Modifying
	@Transactional
    @Query(value ="DELETE FROM CG_REGISTRATION_MASTER C WHERE C.USER_ID = :userId", nativeQuery = true)
	int deleteByUserId(String userId);

}
