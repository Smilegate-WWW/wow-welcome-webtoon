package com.www.auth.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idx;
	
	@Column(length=20, nullable=false)
	private String userid;
	
	@Column(length = 64, nullable = false)
	private String pw;
	
	@Column(length = 12, nullable = false)
	private String name;
	
	@Column(nullable = false)
	private Date birth;
	
	@Column(nullable = false)
	private int gender;
	
	@Column(nullable = false, length=45)
	private String email;
	
	@Column(nullable = false)
	private LocalDateTime register_date;
	
	@Column(nullable = false)
	private LocalDateTime updated_date;
	
	@Column
	private LocalDateTime login_date;
	
	
	@Builder
    public Users(String id, String pw, String name, Date birth, int gender, String email) {
        this.userid = id;
        this.pw = pw;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.email = email;
        register_date = LocalDateTime.now();
        updated_date = register_date;
    }

}
