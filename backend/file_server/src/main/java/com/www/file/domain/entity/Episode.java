package com.www.file.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@SequenceGenerator(
		name = "WEBTOON_SEQ_GENERATOR",
		sequenceName = "WEBTOON_SEQ" ,
		initialValue = 1, allocationSize = 1
)
public class Episode {
	
	@Id
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator =
	"WEBTOON_SEQ_GENERATOR" )
	private int idx;
	@Column
	private int webtoon_idx;
	@Column
	private int ep_no;
	@Column
	private String author_comment;
	@Column
	private String thumnail;
	@Column
	private int ep_hits;
	
	//created_date
	//updated_date
	@Builder
	public Episode(int idx, int webtoon_idx, int ep_no, String author_comment, 
			String thumnail, int ep_hits) {
		this.idx = idx;
		this.webtoon_idx = webtoon_idx;
		this.ep_no = ep_no;
		this.author_comment = author_comment;
		this.thumnail = thumnail;
		this.ep_hits = ep_hits;
		
	}
	

}
