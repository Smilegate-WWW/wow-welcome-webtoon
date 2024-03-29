package com.www.auth.controller;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.www.auth.dto.Response;
import com.www.auth.dto.Tokens;
import com.www.auth.dto.UserDto;
import com.www.auth.dto.UserInfoDto;
import com.www.auth.dto.UserInfoModifiedDto;
import com.www.auth.dto.UserLoginDto;
import com.www.auth.dto.UserRegisterDto;
import com.www.auth.service.JwtTokenProvider;
import com.www.auth.service.UserService;

import lombok.AllArgsConstructor;

/**
 * User Register, Login API
 * 
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
	 * POST(/users) 회원가입
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping
	public Response<UserRegisterDto> SignUP(@RequestBody UserRegisterDto user) {
		System.out.println("======================");
		Response<UserRegisterDto> result = new Response<UserRegisterDto>();
		result.setCode(userService.register(user));
		switch (result.getCode()) {
		case 0: // 회원가입 성공
			result.setMsg("insert complete");
			result.setData(user);
			break;
		case 1: // 회원가입 실패
			result.setMsg("insert fail : already used id");
			break;
		case 3: // 회원가입 실패
			result.setMsg("insert fail : already used email");
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
		if (tokens.getAccessToken() != null) { // 로그인 성공
			result.setCode(0);
			result.setMsg("login complete");
			// json return
			UserInfoDto info = new UserInfoDto(userService.getUserDto(userlogin.getUserid()), tokens.getRefreshToken());
			result.setData(info);
			response.addHeader(HttpHeaders.AUTHORIZATION, "bearer " + tokens.getAccessToken());
		} else { // 로그인 실패
			result.setCode(2);
			result.setMsg("auth fail: 'not exist id' or 'wrong pw'");
		}
		return result;
	}

	/**
	 * DELETE(/users/token) 로그아웃
	 * 
	 * @param AccessToken  (auth header)
	 * @param RefreshToken (request body)
	 * @return expire tokens
	 */
	@DeleteMapping("/{idx}/token")
	public Response<String> Logout(@RequestHeader("Authorization") String AccessToken, @RequestBody String data,
			@PathVariable("idx") int useridx) {

		Response<String> result = new Response<String>();
		// access token bearer split
		AccessToken = AccessToken.substring(7);
		JSONObject body = new JSONObject(data);
		String RefreshToken = body.getString("RefreshToken");
		System.out.println("====Idx:" + useridx + "=====");
		System.out.println("AccessToken:" + AccessToken);
		System.out.println("RefreshToken:" + RefreshToken);

		int r = jwtTokenProvider.checkRefreshToken(AccessToken, RefreshToken, useridx);
		switch (r) {
		case 41: // 이미 로그아웃된 상태
			result.setCode(41);
			result.setMsg("access denied: already logout");
			break;
		case 40: // refresh token 파기
		case 43:
			jwtTokenProvider.expireToken(useridx);
			result.setCode(0);
			result.setMsg("request complete:logout");
			break;
		case 42: // 에러 존재
			result.setCode(42);
			result.setMsg("access denied : maybe captured or faked token");
			break;
		}

		return result;
	}

	/**
	 * POST (/users/{user_idx}/token) access token 재발급
	 * 
	 * @param AccessToken
	 * @param data
	 * @param useridx
	 * @param response
	 * @return response header濡� access token �쟾�넚
	 */
	@PostMapping("/{idx}/token")
	public Response<String> ReissueToken(@RequestHeader("Authorization") String AccessToken, @RequestBody String data,
			@PathVariable("idx") int useridx, HttpServletResponse response) {
		Response<String> result = new Response<String>();
		// access token bearer split
		AccessToken = AccessToken.substring(7);
		JSONObject body = new JSONObject(data);
		String RefreshToken = body.getString("RefreshToken");
		System.out.println("====Idx:" + useridx + "=====");
		System.out.println("AccessToken:" + AccessToken);
		System.out.println("RefreshToken:" + RefreshToken);

		int r = jwtTokenProvider.checkRefreshToken(AccessToken, RefreshToken, useridx);
		switch (r) {
		case 40: // 재발급 (code 0)
			String newAT = jwtTokenProvider.createAccessToken(useridx, jwtTokenProvider.getUserName(AccessToken));
			response.addHeader(HttpHeaders.AUTHORIZATION, "bearer " + newAT);
			result.setCode(0);
			result.setMsg("request complete : reissue tokens");
			break;
		case 41: // 로그아웃된 상태(토큰 만료)
			result.setCode(41);
			result.setMsg("access denied: logout, you need to re-login");
			break;
		case 42: // 에러
		case 43:
			result.setCode(42);
			result.setMsg("access denied: incorrect access");
			break;
		// case 43:
		// result.setCode(43);
		// result.setMsg("access denied: access token is not invalid or you need to send
		// type 'delete'");
		}
		return result;

	}

	/**
	 * 회원정보수정 (/users/{user_idx})
	 * 
	 * @param AccessToken
	 * @param useridx
	 * @param userinfo
	 * @return
	 */
	@PutMapping("/{idx}")
	public Response<String> modifyUserInfo(@RequestHeader("Authorization") String AccessToken,
			@PathVariable("idx") int useridx, @RequestBody UserInfoModifiedDto userinfo) {
		Response<String> result = new Response<String>();
		// access token bearer split
		AccessToken = AccessToken.substring(7);

		// access token 유효한가
		switch (jwtTokenProvider.validateToken(AccessToken)) {
		case 1:
			result.setCode(44);
			result.setMsg("access denied : expired access token");
			return result;
		case 2:
			result.setCode(42);
			result.setMsg("access denied : maybe captured or faked token");
			return result;
		case 0:
			if (useridx != jwtTokenProvider.getUserIdx(AccessToken)) {
				result.setCode(42);
				result.setMsg("access denied : maybe captured or faked token");
				return result;
			}
			break;
		}
		// user info update
		int update_result = userService.modifyInfo(useridx, userinfo);
		if (update_result == 1) {
			result.setCode(0);
			result.setMsg("request complete : modify the user info");
		} else {
			System.out.println("UPDATE RESULT : " + update_result);
			result.setCode(1);
			result.setMsg("post request fail : sql update error");
		}
		return result;
	}

	/**
	 * 회원 탈퇴 (/users/{user_idx})
	 * @param AccessToken
	 * @param useridx
	 * @return
	 */
	@DeleteMapping("/{idx}")
	public Response<String> Withdraw(@RequestHeader("Authorization") String AccessToken,
			@PathVariable("idx") int useridx) {
		Response<String> result = new Response<String>();
		// access token bearer split
		AccessToken = AccessToken.substring(7);

		// access token 유효한가
		switch (jwtTokenProvider.validateToken(AccessToken)) {
		case 1:
			result.setCode(44);
			result.setMsg("access denied : expired access token");
			return result;
		case 2:
			result.setCode(42);
			result.setMsg("access denied : maybe captured or faked token");
			return result;
		case 0:
			if (useridx != jwtTokenProvider.getUserIdx(AccessToken)) {
				result.setCode(42);
				result.setMsg("access denied : maybe captured or faked token");
				return result;
			}
			break;
		}
		//회원정보 삭제
		try {
		userService.deleteInfo(useridx);
		}catch(Exception e) {
			result.setCode(4);
			result.setMsg("delete fail: sql error");
			return result;
		}
		result.setCode(0);
		result.setMsg("request complete: withdraw user");
		return result;
	}

}
