package com.www.auth.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.www.auth.dto.Tokens;
import com.www.auth.dto.UserLoginDto;
import com.www.auth.dto.UserRegisterDto;
import com.www.auth.entity.Users;
import com.www.auth.respository.UsersRepository;

import com.www.auth.service.JwtTokenProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	UsersRepository userRepository;
	PasswordEncoder passwordEncoder;
	JwtTokenProvider jwtTokenProvider;

	/**
	 * REGISTER 회원가입
	 * @param userRegisterDto(회원가입 정보)
	 * @return result error code 
	 */
	public int register(UserRegisterDto user) {
		// user id 중복 검사
		// id중복일경우 code, msg 추가
		if (userRepository.existsByUserid(user.getUserid())) {
			return 4; //insert fail
		}
		// id중복아닐경우
		// pw encoding
		String encodedpw = passwordEncoder.encode(user.getPw());
		// insert
		userRepository.save(user.toEntity(encodedpw));
		// insert complete
		return 0; //insert compelete
	}

	/**
	 * LOGIN 로그인
	 * @param userlogindto (id/pw)
	 * @return tokens (access/refresh)
	 */
	public Tokens login(UserLoginDto user) {
		Tokens tokens = new Tokens();
		// user login
		// id not exist
		if (!userRepository.existsByUserid(user.getUserid())) {
			return tokens; //error 3 : login error not exist id
		}
		// pw matching
		Users info = userRepository.findByUserid(user.getUserid()).get(0);
		if (passwordEncoder.matches(user.getPw(), info.getPw())) {
			System.out.println("============"+info.getName());
			// code 1 : login success
			tokens.setAccessToken(jwtTokenProvider.createAccessToken(info.getIdx(),info.getName()));
			System.out.println("access token:"+tokens.getAccessToken());
			tokens.setRefreshToken(jwtTokenProvider.createRefreshToken(info.getName()));
            System.out.println("refresh token:"+tokens.getRefreshToken());
			// 로그인 시간 찍히는것 수정필요 ==================
			
		} 
		//pw no matching code 2 : login fail 
		return tokens;
	}
}
