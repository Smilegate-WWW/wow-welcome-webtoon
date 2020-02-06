package com.www.auth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.www.auth.dto.Response;
import com.www.auth.dto.Tokens;
import com.www.auth.dto.UserDto;
import com.www.auth.dto.UserLoginDto;
import com.www.auth.dto.UserRegisterDto;
import com.www.auth.service.UserService;

import lombok.AllArgsConstructor;

/**
 * User Register, Login API
 * 
 * @author ji-water
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class AuthController {

	private UserService userService;

	/**
	 * (/users)SingUp
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping
	public Response<UserRegisterDto> execSignUP(@RequestBody UserRegisterDto user) {
		System.out.println("======================");
		Response<UserRegisterDto> result = new Response<UserRegisterDto>();
		result.setCode(userService.register(user));
		switch (result.getCode()) {
		case 0:
			result.setMsg("insert complete");
			result.setData(user);
			break;
		case 1:
			result.setMsg("insert fail");
			break;
		}
		return result;
	}

	/**
	 * LOGIN
	 * @param userlogindto (id/pw)
	 * @return auth header: access token, body : refresh token
	 */
	@PostMapping("/token")
	public Response<String> Login(HttpServletResponse response,UserLoginDto userlogin) {
		Response<String> result = new Response<String>();
		Tokens tokens=userService.login(userlogin);
		if(tokens.getAccessToken()!=null) {
			result.setCode(0);
			result.setMsg("login complete");
			result.setData(tokens.getRefreshToken());
			response.addHeader(HttpHeaders.AUTHORIZATION, "bearer "+tokens.getAccessToken());
		}
		else {
			result.setCode(2);
			result.setMsg("auth fail: 'not exist id' or 'wrong pw'");
		}
		return result;
	}

	/*
	 * System.out.println("======token create=========="); if
	 * (jwtTokenProvider.validateToken(token)) System.out.println("true!");
	 * System.out.println(jwtTokenProvider.getUserID(token));
	 * System.out.println(jwtTokenProvider.getUserIdx(token));
	 * System.out.println("============================");
	 */

}
