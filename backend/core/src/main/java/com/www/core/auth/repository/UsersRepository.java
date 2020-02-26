package com.www.core.auth.repository;

import com.www.core.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * Repository : JPA connects DB
 * @author bjiso
 *
 */
public interface UsersRepository extends JpaRepository<Users, Integer>{

	Users findByUserid(String userid);
	Users findByIdx(int idx);
	
	boolean existsByUserid(String userid);
	boolean existsByEmail(String email);
	
	@Modifying
	@Transactional
	@Query("UPDATE Users SET login_date = :logindate WHERE idx = :idx")
	int updateLoginDate(@Param("logindate") LocalDateTime login_date, @Param("idx") int idx);
	
	@Modifying
	@Transactional
	@Query("UPDATE Users "
			+ "SET "
			+ "pw = :encoded_pw,"
			+ "gender = :gender,"
			+ "name = :name,"
			+ "birth = :birth "
			+ "WHERE idx = :idx")
	int updateUserInfo(@Param("encoded_pw") String encoded_pw, @Param("gender")int gender, @Param("name")String name,@Param("birth")Date birth,@Param("idx")int user_idx);
	
	@Modifying
	@Transactional
	@Query("UPDATE Users SET updated_date = :updated_date WHERE idx = :idx")
	int updateUpdatedDate(@Param("updated_date") LocalDateTime updated_date, @Param("idx") int idx);
}
