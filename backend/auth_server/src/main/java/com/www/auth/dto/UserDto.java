package com.www.auth.dto;

import java.sql.Date;

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
public class UserDto {
	
		private String ID;
		private String Name;
		private Date Birth;
		private int sex;
		
	
}
