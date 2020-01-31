package com.www.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.www.auth.dto.Response;
import com.www.auth.dto.UserRegisterDto;
import com.www.auth.respository.UsersRepository;

import lombok.AllArgsConstructor;

/**
 * User Register, Login API
 * @author ji-water
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class AuthController {

	private UsersRepository userRepository;
	
	/**
	 * User Input -> mysql Users table just insert
	 * @param user
	 * @return 
	 */
	@PostMapping
	public Response<UserRegisterDto> execSignUP(@RequestBody UserRegisterDto user){
		userRepository.save(user.toEntity()); //DB insert
		Response<UserRegisterDto> result = new Response<UserRegisterDto>();
		result.setResponse(user);
		return result;
	}
	
	
}
