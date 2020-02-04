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
	private long tokenValidMilisecond = 1000L * 60 * 60; // 1�ð��� ��ū ��ȿ
    
    // Jwt ��ū ����
	public String createToken(UserDto user) {
        Claims claims = Jwts.claims().setSubject(user.getUserid());
        claims.put("idx", user.getIdx());
        claims.put("name",user.getName());
        
        Date now = new Date();
        return Jwts.builder()
        		.setHeaderParam("typ","jwt")
                .setClaims(claims) // ������
                .setIssuedAt(now) // ��ū ��������
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // set Expire Time
                .signWith(KEY,signatureAlgorithm) // ��ȣȭ �˰���, secret�� ����
                .compact();
    }
 
    // Jwt ��ū���� ȸ�� ���� ���� ����
    public String getUserID(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    
    //jwt token���� idx����
    public int getUserIdx(String token) {
    	return (int) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("idx");
    }
 
    // Request�� Header���� token �Ľ� : "X-AUTH-TOKEN: jwt��ū"
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-AUTH-TOKEN");
    }
 
    // Jwt ��ū�� ��ȿ�� + �������� Ȯ��
    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
