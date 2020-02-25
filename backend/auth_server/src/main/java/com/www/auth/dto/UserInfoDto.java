package com.www.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserInfoDto : 로그인 성공시 클라이언트에 반환하는dto (회원가입시 정보 + refresh token)
 * @author bjiso
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class UserInfoDto extends UserDto{

	String token;
	
	public UserInfoDto(UserDto dto,String token) {
		super(dto.getUserid(),dto.getName(),dto.getBirth(),dto.getGender(),dto.getEmail());
		this.token = token;
	}
}
