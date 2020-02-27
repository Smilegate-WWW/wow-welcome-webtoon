package com.www.auth.controller;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.www.auth.dto.Response;
import com.www.auth.dto.Tokens;
import com.www.auth.dto.UserDto;
import com.www.auth.dto.UserInfoDto;
import com.www.auth.dto.UserLoginDto;
import com.www.auth.dto.UserRegisterDto;
import com.www.auth.service.JwtTokenProvider;
import com.www.auth.service.UserService;

import lombok.AllArgsConstructor;

/**
 * User Register, Login API
 * @author ji-water
 */
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class AuthController {

	private UserService userService;
	private JwtTokenProvider jwtTokenProvider;

	@GetMapping
	public String test() {
		return "GET MAPPING USERS !!!!!!!!!!! ~~~";
	}
	
	/**
	 * POST(/users) �쉶�썝媛��엯
	 * @param user
	 * @return
	 */
	@PostMapping
	public Response<UserRegisterDto> execSignUP(@RequestBody UserRegisterDto user) {
		System.out.println("======================");
		Response<UserRegisterDto> result = new Response<UserRegisterDto>();
		result.setCode(userService.register(user));
		switch (result.getCode()) {
		case 0: //�쉶�썝媛��엯 �꽦怨�
			result.setMsg("insert complete");
			result.setData(user);
			break;
		case 1: //�쉶�썝媛��엯 �떎�뙣
			result.setMsg("insert fail");
			break;
		}
		return result;
	}

	/**
	 * POST(/users/token) LOGIN
	 * 
	 * @param userlogindto (id/pw)
	 * @return auth header: access token, body : refresh token & user info
	 */
	@PostMapping("/token")
	public Response<UserInfoDto> Login(HttpServletResponse response, @RequestBody UserLoginDto userlogin) {
		Response<UserInfoDto> result = new Response<UserInfoDto>();
		Tokens tokens = userService.login(userlogin);
		if (tokens.getAccessToken() != null) { //濡쒓렇�씤 �꽦怨�
			result.setCode(0);
			result.setMsg("login complete");
			//json return
			UserInfoDto info = new UserInfoDto(userService.getUserDto(userlogin.getUserid()),tokens.getRefreshToken());
			result.setData(info);
			response.addHeader(HttpHeaders.AUTHORIZATION, "bearer " + tokens.getAccessToken());
		} else { //濡쒓렇�씤 �떎�뙣
			result.setCode(2);
			result.setMsg("auth fail: 'not exist id' or 'wrong pw'");
		}
		return result;
	}

	/**
	 * DELETE(/users/token) 濡쒓렇�븘�썐
	 * @param AccessToken (auth header)
	 * @param RefreshToken (request body)
	 * @return expire tokens
	 */
	@DeleteMapping("/{idx}/token")
	public Response<String> Logout(@RequestHeader("Authorization") String AccessToken,
			@RequestBody String data,@PathVariable("idx") int useridx) {
		
		Response<String> result = new Response<String>();
		// access token bearer split
		AccessToken = AccessToken.substring(7);
		JSONObject body = new JSONObject(data);
		String RefreshToken = body.getString("RefreshToken");
		System.out.println("====Idx:"+useridx+"=====");
		System.out.println("AccessToken:"+AccessToken);
		System.out.println("RefreshToken:"+RefreshToken);

		int r=jwtTokenProvider.checkRefreshToken(AccessToken, RefreshToken,useridx);
		switch(r){
			case 41: //�씠誘� 濡쒓렇�븘�썐�맂 �긽�깭
				result.setCode(41);
				result.setMsg("access denied: already logout");
				break;
			case 40: //refresh token �뙆湲�
			case 43:
				jwtTokenProvider.expireToken(useridx);
				result.setCode(0);
				result.setMsg("request complete:logout");
				break;
			case 42: //�뿉�윭 議댁옱
				result.setCode(42);
				result.setMsg("access denied : maybe captured or faked token");
				break;
		}

		return result;
	}
	
	/**
	 * POST (/users/{user_idx}/token) access token �옱諛쒓툒
	 * @param AccessToken
	 * @param data
	 * @param useridx
	 * @param response
	 * @return response header濡� access token �쟾�넚
	 */
	@PostMapping("/{idx}/token")
	public Response<String> ReissueToken(@RequestHeader("Authorization") String AccessToken,
			@RequestBody String data,@PathVariable("idx") int useridx,HttpServletResponse response){
		Response<String> result = new Response<String>();
		// access token bearer split
		AccessToken = AccessToken.substring(7);
		JSONObject body = new JSONObject(data);
		String RefreshToken = body.getString("RefreshToken");
		System.out.println("====Idx:"+useridx+"=====");
		System.out.println("AccessToken:"+AccessToken);
		System.out.println("RefreshToken:"+RefreshToken);
		
		int r=jwtTokenProvider.checkRefreshToken(AccessToken, RefreshToken, useridx);
		switch(r) {
		case 40: //�옱諛쒓툒 (code 0)
			String newAT=jwtTokenProvider.createAccessToken(useridx, jwtTokenProvider.getUserName(AccessToken));
			response.addHeader(HttpHeaders.AUTHORIZATION, "bearer " + newAT);
			result.setCode(0);
			result.setMsg("request complete : reissue tokens");
			break;
		case 41: //濡쒓렇�븘�썐�맂 �긽�깭(�넗�겙 留뚮즺)
			result.setCode(41);
			result.setMsg("access denied: logout, you need to re-login");
			break;
		case 42: //�뿉�윭
		case 43:
			result.setCode(42);
			result.setMsg("access denied: incorrect access");
			break;
		//case 43:
		//	result.setCode(43);
		//	result.setMsg("access denied: access token is not invalid or you need to send type 'delete'");
		}
		return result;
		
	}

}
