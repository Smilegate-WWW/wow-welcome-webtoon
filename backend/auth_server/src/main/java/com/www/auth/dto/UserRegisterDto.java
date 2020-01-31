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
	private String Password;
	
	public Users toEntity() {
		return Users.builder()
				.id(getID())
				.name(getName())
				.pw(Password)
				.birth(getBirth())
				.sex(getSex())
				.build();
	}
	
}
