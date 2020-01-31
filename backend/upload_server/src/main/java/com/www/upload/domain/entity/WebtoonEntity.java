package com.www.upload.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Data
@Entity
public class WebtoonEntity {
	
	@Id
	private int webtoon_id;
	@Column
	private String title;
	@Column
	private int form;
	@Column
	private int genre1;
	@Column
	private int genre2;
	@Column
	private String plot;
	
	public WebtoonEntity(int webtoon_id,String title,int form, int genre1, int genre2, String plot) {
		this.webtoon_id = webtoon_id;
		this.title = title;
		this.form = form;
		this.genre1 = genre1;
		this.genre2 = genre2;
	}
	
	
	
}
