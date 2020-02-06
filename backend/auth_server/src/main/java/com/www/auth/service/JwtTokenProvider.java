package com.www.auth.service;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import com.www.auth.dto.UserDto;

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
    
    // Jwt 토큰 생성
	public String createToken(UserDto user) {
        Claims claims = Jwts.claims().setSubject(user.getUserid());
        claims.put("idx", user.getIdx());
        claims.put("name",user.getName());
        
        Date now = new Date();
        return Jwts.builder()
        		.setHeaderParam("typ","jwt")
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // set Expire Time
                .signWith(KEY,signatureAlgorithm) // 암호화 알고리즘, secret값 세팅
                .compact();
    }
 
    // Jwt 토큰에서 회원 구별 정보 추출
    public String getUserID(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    
    //jwt token에서 idx추출
    public int getUserIdx(String token) {
    	return (int) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("idx");
    }
 
    // Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }
 
    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
