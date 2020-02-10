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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JwtTokenProvider{
	
    private String secretKey="secretkeyhelloimjwtsecretkeysecretkeyhelloimkeyjwtplz";
	
	private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	private byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
	private Key KEY = new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());	
	private long tokenValidMilisecond = 1000L * 60 * 60; // 1시간만 토큰 유효
    
	@Autowired
	RedisTemplate<String,Object> redisTemplate;
	
    // access jwt 토큰 생성
	public String createAccessToken(int user_idx, String user_name) {
		//payload
        Claims claims = Jwts.claims();
        claims.put("user_idx", user_idx);
        claims.put("user_name",user_name);
        
        Date now = new Date();
        return Jwts.builder()
        		.setHeaderParam("typ","jwt")
                .setClaims(claims) 
                .setIssuedAt(now) 
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) 
                .signWith(KEY,signatureAlgorithm) 
                .compact();
    }
	//refresh token 
	public String createRefreshToken(String user_name) {
		System.out.println("redis template:"+redisTemplate);
		
		Date now = new Date();
		String refreshToken = Jwts.builder()
				.setHeaderParam("typ","jwt")
				.setIssuedAt(now) 
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond*24)) //일단 하루만 유효 
                .signWith(KEY,signatureAlgorithm) 
                .compact();
		//redis set (유효시간 설정 필요)
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		vop.set(user_name, refreshToken);
		redisTemplate.expire(user_name, 100000, TimeUnit.MILLISECONDS);
		
		return refreshToken;
	}
    
    //jwt token에서 idx추출
    public int getUserIdx(String token) {
    	return (int) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("user_idx");
    }
    
    //jwt token에서 name 추출
    public String getUserName(String token) {
    	return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("user_name");
    }
 
    // Jwt 토큰의 유효성 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    //refresh token 검증
    public boolean checkRefreshToken(String accessT, String refreshT) {
    	String user_name = getUserName(accessT);
    	try {
    		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
    		String redis_T = (String) vop.get(user_name);
    		System.out.println("redis refresh token: "+redis_T);
    		
    		if(redis_T.equals(refreshT))
    			return true;
    		else
    			return false;
    	}catch(Exception e) {
    		return false;
    	}
    	
    }
}
