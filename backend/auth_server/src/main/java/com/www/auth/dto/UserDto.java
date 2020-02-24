package com.www.auth.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserDto : not contains pw
 * @author bjiso
 *
 */

//controller dto
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
		//private int idx;
		private String userid;
		private String name;
		private Date birth;
		private int gender;
		private String email;
		
}
