package com.www.auth.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Users Entity (DB User info table mapping)
 * @author bjiso
 *
 */
@NoArgsConstructor
@Getter
@Entity
public class Users {
	
	@Id
	private String ID;
	
	@Column(length = 20, nullable = false)
	private String Password;
	
	@Column(length = 12, nullable = false)
	private String Name;
	
	@Column(nullable = false)
	private Date Birth;
	
	@Column(nullable = false)
	private int sex;
	
	@Builder
    public Users(String id, String pw, String name, Date birth, int sex) {
        this.ID = id;
        this.Password = pw;
        this.Name = name;
        this.Birth = birth;
        this.sex = sex;
    }

}
