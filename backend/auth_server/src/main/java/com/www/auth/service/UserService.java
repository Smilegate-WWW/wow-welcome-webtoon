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
	 * REGISTER ȸ������
	 * @param userRegisterDto(ȸ������ ����)
	 * @return result error code 
	 */
	public int register(UserRegisterDto user) {
		// user id �ߺ� �˻�
		// id�ߺ��ϰ�� code, msg �߰�
		if (userRepository.existsByUserid(user.getUserid())) {
			return 4; //insert fail
		}
		// id�ߺ��ƴҰ��
		// pw encoding
		String encodedpw = passwordEncoder.encode(user.getPw());
		// insert
		userRepository.save(user.toEntity(encodedpw));
		// insert complete
		return 0; //insert compelete
	}

	/**
	 * LOGIN �α���
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
			// �α��� �ð� �����°� �����ʿ� ==================
			
		} 
		//pw no matching code 2 : login fail 
		return tokens;
	}
}
