package com.www.core.common;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

@Service
/**
 * Token Checker : token이 유효한지 판단
 * 
 * @author bjiso
 *
 */
public class TokenChecker {

	private String secretKey = "secretkeyhelloimjwtsecretkeysecretkeyhelloimkeyjwtplz";

	/**
	 * access token에서 user idx 가져오는 함수
	 * @param token
	 * @return user_idx (만료된 토큰이라도 형식이 유효한 토큰이라면 idx 반환)
	 */
	public int getUserIdx(String token) {
		try {
			// access token bearer split
			token = token.substring(7);
			return Integer.parseInt(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("user_idx").toString());
		} catch (ExpiredJwtException e) { //만료된 token이라도 user idx 반환
			return (int) e.getClaims().get("user_idx");
		} catch (Exception e) {
			return -1; //error
		}
	}

	/**
	 * access token에서 user name 가져오는 함수
	 * @param token
	 * @return user_name (만료된 토큰이라도 형식이 유효한 토큰이면 user name 반환)
	 */
	public String getUserName(String token) {
		try {
			// access token bearer split
			token = token.substring(7);
			return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("user_name");
		} catch (ExpiredJwtException e) { //유효시간 만료된 경우에도 username 반환
			return (String) e.getClaims().get("user_name");
		} catch (Exception e) { //error
			return e.getMessage();
		}
	}
	
	/**
	 * access token 유효한지 검사하는 함수
	 * @param jwtToken
	 * @return 0 (유효), 1 (만료), 2 (에러,올바르지 않은 토큰)
	 * 플랫폼서버는 결과가 0일 경우에만 서비스를 제공합니다.
	 */
	public int validateToken(String token) {
		try {
			// access token bearer split
			token = token.substring(7);
			Date now = new Date();
			long time = (Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration()
					.getTime() - now.getTime());
			if (time < 1000L * 30) { //exp time 30초 미만일 경우 만료로 간주 
				return 1; 
			}
			return 0; // 유효
		} catch (ExpiredJwtException e) {
			return 1; // 만료
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 2; // 올바르지않은 토큰
		}
	}
	

}
