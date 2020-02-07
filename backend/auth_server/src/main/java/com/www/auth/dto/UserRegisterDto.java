package com.www.auth.dto;

import com.www.auth.entity.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserRegisterDto : User input -> UserRegisterDto -> Entity -> DB
 * @author bjiso
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDto extends UserDto{
	
	private String email;
	private String pw;
	
	public Users toEntity(String e_pw) {
		return Users.builder()
				.id(getUserid())
				.name(getName())
				.e_pw(e_pw)
				.birth(getBirth())
				.gender(getGender())
				.email(email)
				.build();
	}
	
}
