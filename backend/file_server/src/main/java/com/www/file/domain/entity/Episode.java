package com.www.file.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.ManyToAny;

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
public class Episode extends TimeEntity{
	
	@Id
	private int idx;
	@Column
	private int ep_no;
	@Column
	private String author_comment;
	@Column
	private String thumnail;
	@Column
	private String contents;
	@Column
	private int ep_hits;
	
	@ManyToOne
	@JoinColumn(name="webtoon_idx")
	private Webtoon webtoon;
	
	//created_date
	//updated_date
	
	@Builder
	public Episode(Webtoon webtoon, int ep_no, String author_comment, 
			String thumnail, String contents, int ep_hits) {
		//this.idx = idx;
		this.webtoon = webtoon;
		//this.webtoon_idx = webtoon_idx;
		this.ep_no = ep_no;
		this.author_comment = author_comment;
		this.thumnail = thumnail;
		this.contents = contents;
		this.ep_hits = ep_hits;
		
	}
	

}
