package com.www.auth.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.www.auth.dto.Tokens;
import com.www.auth.dto.UserDto;
import com.www.auth.dto.UserLoginDto;
import com.www.auth.dto.UserRegisterDto;
import com.www.core.auth.entity.Users;
import com.www.core.auth.repository.UsersRepository;

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
	 * @param userRegisterDto
	 * @return result error code 
	 */
	public int register(UserRegisterDto user) {
		// id 중복체크
		if (userRepository.existsByUserid(user.getUserid())) {
			return 1; //insert fail
		}
		// pw encoding
		String encodedpw = passwordEncoder.encode(user.getPw());
		// insert
		userRepository.save(user.toEntity(encodedpw));
		// insert complete
		return 0; 
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
			return tokens; 
		}
		// pw matching
		Users info = userRepository.findByUserid(user.getUserid());
		if (passwordEncoder.matches(user.getPw(), info.getPw())) {
			System.out.println("============"+info.getName());
			tokens.setAccessToken(jwtTokenProvider.createAccessToken(info.getIdx(),info.getName()));
			System.out.println("access token:"+tokens.getAccessToken());
			tokens.setRefreshToken(jwtTokenProvider.createRefreshToken(info.getUserid()));
            System.out.println("refresh token:"+tokens.getRefreshToken());
			//login date time
            LocalDateTime now = LocalDateTime.now();
            userRepository.updateLoginDate(now, info.getIdx());
		} 
		return tokens;
	}
	
	public UserDto getUserDto(String user_id) {
		UserDto userDto = new UserDto();
		Users info = userRepository.findByUserid(user_id);
		userDto.setBirth(info.getBirth());
		userDto.setGender(info.getGender());
		userDto.setName(info.getName());
		userDto.setUserid(user_id);
		return userDto;
	}
}
