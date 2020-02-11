package com.www.auth.service;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.*;

@Service
public class JwtTokenProvider {

	private String secretKey = "secretkeyhelloimjwtsecretkeysecretkeyhelloimkeyjwtplz";

	private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	private byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
	private Key KEY = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	private long tokenValidMilisecond = 1000L * 60 * 60; // 유효 1시간(1000*60*60)

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	// access jwt 토큰 생성
	public String createAccessToken(int user_idx, String user_name) {
		// payload
		Claims claims = Jwts.claims();
		claims.put("user_idx", user_idx);
		claims.put("user_name", user_name);

		Date now = new Date();
		return Jwts.builder().setHeaderParam("typ", "jwt").setClaims(claims).setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + tokenValidMilisecond)).signWith(KEY, signatureAlgorithm)
				.compact();
	}

	// refresh token
	public String createRefreshToken(String user_name) {
		System.out.println("redis template:" + redisTemplate);

		Date now = new Date();
		String refreshToken = Jwts.builder().setHeaderParam("typ", "jwt").setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + tokenValidMilisecond * 24)) // 일단 하루만 유효
				.signWith(KEY, signatureAlgorithm).compact();
		// redis set
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		vop.set(user_name, refreshToken);
		// 24시간 이후 refresh token 자동 삭제
		redisTemplate.expire(user_name, tokenValidMilisecond * 24, TimeUnit.MILLISECONDS); 

		return refreshToken;
	}

	// jwt token에서 idx추출
	public int getUserIdx(String token) {
		try {
			return (int) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("user_idx");
		} catch (ExpiredJwtException e) {
			return (int) e.getClaims().get("user_idx");
		}
	}

	// jwt token에서 name 추출
	public String getUserName(String token) {
		try {
			return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("user_name");
		} catch (ExpiredJwtException e) {
			return (String) e.getClaims().get("user_name");
		}
	}

	// Jwt 토큰의 유효성 확인
	public int validateToken(String jwtToken) {
		System.out.println("validateToken parameter:"+jwtToken);
		try {
			Date now = new Date();
			long time = (Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody().getExpiration().getTime()-now.getTime());
			System.out.println("남은 시간! : "+time);
			if(time < 1000L*30) {
				return 1; //30초 남았을 경우에도 expired됐다고 판단
			}
			return 0; // 유효
		} catch (ExpiredJwtException e) {
			return 1; // expired
		} catch (Exception e) {
			System.out.println("validateToken 에러!!!:"+e);
			return 2; // 구조적인 문제 있음
		}
	}

	// refresh token 검증
	public int checkRefreshToken(String accessT, String refreshT, int idx) {
		// 토큰 유효한지 검사 (access token은 재발급 필요한 상태여야함!)
		if (validateToken(accessT) == 1 && validateToken(refreshT) < 2 && getUserIdx(accessT) == idx) {
			String user_name = getUserName(accessT);
			try {
				ValueOperations<String, Object> vop = redisTemplate.opsForValue();
				String redis_T = (String) vop.get(user_name);
				System.out.println("redis refresh token: " + redis_T);

				if (redis_T == null)
					return 41; // 재 로그인 필요 (로그아웃)
				if (redis_T.equals(refreshT))
					return 40; // access token 재발급
				else
					return 42; // 받은 refresh token과 저장된 refresh token 같지않음
			} catch (Exception e) {
				System.out.println(e);
				return 42; // error 존재
			}
		}
		return 42; // 유효하지않은 토큰들
	}

	// redis refresh token 삭제
	public void expireToken(String name) {
		try {
			redisTemplate.delete(name);
		} catch (Exception e) {
			System.out.println("삭제 에러발생!!!!" + e);
		}
	}
}
