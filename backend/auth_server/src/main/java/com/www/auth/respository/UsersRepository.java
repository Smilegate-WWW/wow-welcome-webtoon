package com.www.auth.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.www.auth.entity.Users;

/**
 * Repository : JPA connects DB
 * @author bjiso
 *
 */
public interface UsersRepository extends JpaRepository<Users,String>{

	List<Users> findByUserid(String userid);
	
	boolean existsByUserid(String userid);
}
