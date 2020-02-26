package com.www.auth.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoModifiedDto {
	private String name;
	private Date birth;
	private int gender;
	private String pw;
}
