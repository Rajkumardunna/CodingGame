package com.game.codingGame.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.game.codingGame.model.CGRegistration;

import jakarta.transaction.Transactional;
@Repository
public interface CGRepository extends JpaRepository<CGRegistration, Integer>{
	
	@Query(value = "SELECT * FROM CG_REGISTRATION_MASTER WHERE USER_ID = :userId", nativeQuery = true)
	Optional<CGRegistration> findByUserId(String userId);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE CG_REGISTRATION_MASTER C SET C.USER_ID = :userId WHERE C.SEQ_NUM = :seqNum", nativeQuery = true)
	int updateUserBySeqNum(String userId, int seqNum);
	
	@Modifying
	@Transactional
    @Query(value ="DELETE FROM CG_REGISTRATION_MASTER C WHERE C.USER_ID = :userId", nativeQuery = true)
	int deleteByUserId(String userId);
	
	@Query(value = "SELECT * FROM CG_REGISTRATION_MASTER WHERE OTP = :emailOtp", nativeQuery = true)
	Optional<CGRegistration> findById(int emailOtp);
	
	@Query(value = "SELECT * FROM CG_REGISTRATION_MASTER WHERE OTP = :emailOtp AND email = :email", nativeQuery = true)
	Optional<CGRegistration> findByOtpAndEmail(@Param(value = "emailOtp") int otp,@Param(value ="email") String email);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE CG_REGISTRATION_MASTER C SET C.EDUCATION_QUALIFICATION = :educationQualification, C.PROGRAMMING_LANGUAGE = :programmingLanguage, C.GENDER = :gender, C.LOCATION = :location, C.PASSWORD = :password WHERE C.USER_ID = :userId", nativeQuery = true)
	int updatePersonaDetails(
	    @Param("educationQualification") String educationQualification,
	    @Param("programmingLanguage") String programmingLanguage,
	    @Param("gender") String gender,
	    @Param("location") String location,
	    @Param("password") String password,
	    @Param("userId") String userId);
}
