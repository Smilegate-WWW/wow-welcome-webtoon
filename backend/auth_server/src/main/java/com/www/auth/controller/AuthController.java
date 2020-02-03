package com.www.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.www.auth.dto.Response;
import com.www.auth.dto.UserDto;
import com.www.auth.dto.UserLoginDto;
import com.www.auth.dto.UserRegisterDto;
import com.www.auth.service.UserService;

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

	UserService userService;
	/**
	 * (/users)SingUp
	 * @param user 
	 * @return 
	 */
	@PostMapping
	public Response<UserRegisterDto> execSignUP(@RequestBody UserRegisterDto user){
		System.out.println("======================");
		Response<UserRegisterDto> result = userService.register(user);
		return result;
	}
	
	@PostMapping("/token")
	public Response<String> Login(UserLoginDto userlogin){
		Response<String> result = userService.login(userlogin);
		return result;
	}
	
	
}
