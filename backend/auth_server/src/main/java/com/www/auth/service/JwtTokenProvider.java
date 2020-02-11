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
	private long tokenValidMilisecond = 1000L * 60 * 60; // ��ȿ 1�ð�(1000*60*60)

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	// access jwt ��ū ����
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
				.setExpiration(new Date(now.getTime() + tokenValidMilisecond * 24)) // �ϴ� �Ϸ縸 ��ȿ
				.signWith(KEY, signatureAlgorithm).compact();
		// redis set
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		vop.set(user_name, refreshToken);
		// 24�ð� ���� refresh token �ڵ� ����
		redisTemplate.expire(user_name, tokenValidMilisecond * 24, TimeUnit.MILLISECONDS); 

		return refreshToken;
	}

	// jwt token���� idx����
	public int getUserIdx(String token) {
		try {
			return (int) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("user_idx");
		} catch (ExpiredJwtException e) {
			return (int) e.getClaims().get("user_idx");
		}
	}

	// jwt token���� name ����
	public String getUserName(String token) {
		try {
			return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("user_name");
		} catch (ExpiredJwtException e) {
			return (String) e.getClaims().get("user_name");
		}
	}

	// Jwt ��ū�� ��ȿ�� Ȯ��
	public int validateToken(String jwtToken) {
		System.out.println("validateToken parameter:"+jwtToken);
		try {
			Date now = new Date();
			long time = (Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody().getExpiration().getTime()-now.getTime());
			System.out.println("���� �ð�! : "+time);
			if(time < 1000L*30) {
				return 1; //30�� ������ ��쿡�� expired�ƴٰ� �Ǵ�
			}
			return 0; // ��ȿ
		} catch (ExpiredJwtException e) {
			return 1; // expired
		} catch (Exception e) {
			System.out.println("validateToken ����!!!:"+e);
			return 2; // �������� ���� ����
		}
	}

	// refresh token ����
	public int checkRefreshToken(String accessT, String refreshT, int idx) {
		// ��ū ��ȿ���� �˻� (access token�� ��߱� �ʿ��� ���¿�����!)
		if (validateToken(accessT) == 1 && validateToken(refreshT) < 2 && getUserIdx(accessT) == idx) {
			String user_name = getUserName(accessT);
			try {
				ValueOperations<String, Object> vop = redisTemplate.opsForValue();
				String redis_T = (String) vop.get(user_name);
				System.out.println("redis refresh token: " + redis_T);

				if (redis_T == null)
					return 41; // �� �α��� �ʿ� (�α׾ƿ�)
				if (redis_T.equals(refreshT))
					return 40; // access token ��߱�
				else
					return 42; // ���� refresh token�� ����� refresh token ��������
			} catch (Exception e) {
				System.out.println(e);
				return 42; // error ����
			}
		}
		return 42; // ��ȿ�������� ��ū��
	}

	// redis refresh token ����
	public void expireToken(String name) {
		try {
			redisTemplate.delete(name);
		} catch (Exception e) {
			System.out.println("���� �����߻�!!!!" + e);
		}
	}
}
