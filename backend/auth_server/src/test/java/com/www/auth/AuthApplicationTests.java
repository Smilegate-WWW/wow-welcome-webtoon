package com.www.auth;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.www.auth.entity.Users;
import com.www.auth.respository.UsersRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthApplicationTests {

	@Autowired
	UsersRepository usersRepository;
	
	@After
    public void cleanup() {
        usersRepository.deleteAll();
    }
	
    @Test
    void contextLoads() {
    	usersRepository.save(Users.builder()
    			.id("IDtest")
    			.pw("test1234")
    			.name("Å×½ºÆ®")
    			.birth(java.sql.Date.valueOf("1970-01-01"))
    			.build()
    			);
    }

}