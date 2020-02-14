package com.www.core.auth.repository;

import com.www.core.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Repository : JPA connects DB
 * @author bjiso
 *
 */
public interface UsersRepository extends JpaRepository<Users, Integer>{

	Users findByUserid(String userid);
	
	boolean existsByUserid(String userid);
	
	@Modifying
	@Transactional
	@Query("UPDATE Users SET login_date = :logindate WHERE idx = :idx")
	int updateLoginDate(@Param("logindate") LocalDateTime login_date, @Param("idx") int idx);
}
