package com.www.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserInfoDto extends UserDto{
	String token;
	
	public UserInfoDto(UserDto dto,String token) {
		super(dto.getIdx(),dto.getUserid(),dto.getName(),dto.getBirth(),dto.getGender());
		this.token = token;
	}
}
