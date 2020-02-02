package com.www.auth.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.www.auth.dto.Response;
import com.www.auth.dto.UserDto;
import com.www.auth.dto.UserLoginDto;
import com.www.auth.dto.UserRegisterDto;
import com.www.auth.entity.Users;
import com.www.auth.respository.UsersRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	UsersRepository userRepository;
	PasswordEncoder passwordEncoder;
	
	/**
	 * register, sign up
	 */
	public Response<UserRegisterDto> register(UserRegisterDto user) {
		Response<UserRegisterDto> result = new Response<UserRegisterDto>();
		//user id 중복 검사
		//id중복일경우 code, msg 추가
		
		//id중복아닐경우
		//pw encoding
		System.out.println("pw:"+user.getPw());
		String encodedpw = passwordEncoder.encode(user.getPw());
		System.out.println(encodedpw);
		//insert
		userRepository.save(user.toEntity(encodedpw));
		//insert complete
		result.setCode(000);
		result.setMsg("insert complete");
		
		return result;
	}
	
	/**
	 * LOGIN
	 * @param userlogindto (id/pw)
	 * @return 
	 */
	public Response<UserDto> login(UserLoginDto user){
		Response<UserDto> result = new Response<UserDto>();
		//user login
		List<Users> userinfo =userRepository.findByUserid(user.getUserid());
		//id not exist
		if(userinfo.isEmpty()) {
			result.setCode(003);
			result.setMsg("login fail");
			return result;
		}
		//pw matching
		Users info = userinfo.get(0);
		if(passwordEncoder.matches(user.getPw(), info.getPw())){
			result.setCode(001);
			result.setMsg("login complete");
			UserDto data = new UserDto();
			data.setBirth(info.getBirth());
			data.setIdx(info.getIdx());
			data.setGender(info.getGender());
			data.setName(info.getName());
			data.setUserid(info.getUserid());
			result.setData(data);
			//로그인 시간 찍히는것 수정필요
		}
		else {
			result.setCode(002);
			result.setMsg("login fail");
		}
		return result;
	}
}
