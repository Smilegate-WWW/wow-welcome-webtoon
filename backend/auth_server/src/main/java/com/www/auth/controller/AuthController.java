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
import com.www.auth.dto.UserLoginDto;
import com.www.auth.dto.UserRegisterDto;
import com.www.auth.service.JwtTokenProvider;
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
	private JwtTokenProvider jwtTokenProvider;

	@GetMapping
	public String test() {
		return "GET MAPPING USERS !!!!!!!!!!! ~~~";
	}
	
	/**
	 * POST(/users) ȸ������ ��û
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
		case 0:// ȸ������ ����
			result.setMsg("insert complete");
			result.setData(user);
			break;
		case 1:// ȸ������ ����
			result.setMsg("insert fail");
			break;
		}
		return result;
	}

	/**
	 * POST(/users/token) LOGIN
	 * 
	 * @param userlogindto (id/pw)
	 * @return auth header: access token, body : refresh token
	 */
	@PostMapping("/token")
	public Response<String> Login(HttpServletResponse response, @RequestBody UserLoginDto userlogin) {
		Response<String> result = new Response<String>();
		Tokens tokens = userService.login(userlogin);
		if (tokens.getAccessToken() != null) { // �α��� ����
			result.setCode(0);
			result.setMsg("login complete");
			result.setData(tokens.getRefreshToken());
			response.addHeader(HttpHeaders.AUTHORIZATION, "bearer " + tokens.getAccessToken());
		} else { // �α��� ����
			result.setCode(2);
			result.setMsg("auth fail: 'not exist id' or 'wrong pw'");
		}
		return result;
	}

	/**
	 * DELETE(/users/token) �α׾ƿ�
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
			case 41: //�̹� �α׾ƿ�
				result.setCode(41);
				result.setMsg("logout");
				break;
			case 40: //refresh token ����
				jwtTokenProvider.expireToken(jwtTokenProvider.getUserName(AccessToken));
				result.setCode(41);
				result.setMsg("logout");
				break;
			case 42:
				result.setCode(42);
				result.setMsg("access denied : maybe captured or faked token");
				break;
		}

		return result;
	}
	
	/**
	 * POST (/users/{user_idx}/token) access token ��߱� 
	 * @param AccessToken
	 * @param data
	 * @param useridx
	 * @param response
	 * @return response header�� access token �߱�
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
		case 40:
			String newAT=jwtTokenProvider.createAccessToken(useridx, jwtTokenProvider.getUserName(AccessToken));
			response.addHeader(HttpHeaders.AUTHORIZATION, "bearer " + newAT);
			result.setCode(40);
			result.setMsg("reissue tokens");
			break;
		case 41:
			result.setCode(41);
			result.setMsg("access denied: logout, you need to re-login");
			break;
		case 42:
			result.setCode(42);
			result.setMsg("access denied: incorrect access");
			break;
		}
		return result;
		
	}

}
