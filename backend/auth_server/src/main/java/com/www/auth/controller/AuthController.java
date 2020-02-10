package com.www.auth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
	 * POST(/users) 회원가입 요청
	 * @param user
	 * @return
	 */
	@PostMapping
	public Response<UserRegisterDto> execSignUP(@RequestBody UserRegisterDto user) {
		System.out.println("======================");
		Response<UserRegisterDto> result = new Response<UserRegisterDto>();
		result.setCode(userService.register(user));
		switch (result.getCode()) {
		case 0://회원가입 성공
			result.setMsg("insert complete");
			result.setData(user);
			break;
		case 1://회원가입 실패
			result.setMsg("insert fail");
			break;
		}
		return result;
	}

	/**
	 * POST(/users/token) LOGIN
	 * @param userlogindto (id/pw)
	 * @return auth header: access token, body : refresh token
	 */
	@PostMapping("/token")
	public Response<String> Login(HttpServletResponse response,@RequestBody UserLoginDto userlogin) {
		Response<String> result = new Response<String>();
		Tokens tokens=userService.login(userlogin);
		if(tokens.getAccessToken()!=null) { //로그인 성공
			result.setCode(0);
			result.setMsg("login complete");
			result.setData(tokens.getRefreshToken());
			response.addHeader(HttpHeaders.AUTHORIZATION, "bearer "+tokens.getAccessToken());
		}
		else { //로그인 실패
			result.setCode(2);
			result.setMsg("auth fail: 'not exist id' or 'wrong pw'");
		}
		return result;
	}

	@DeleteMapping("/token")
	public Response<String> Logout(@RequestHeader("Authentication") String AccessToken, @RequestBody String RefreshToken ){
		Response<String> result = new Response<String>();
		//access token bearer split
		//access token 유효검사
		//refresh token 있는지 체크
		//redis refresh token 버리기
		return result;
	}

}
