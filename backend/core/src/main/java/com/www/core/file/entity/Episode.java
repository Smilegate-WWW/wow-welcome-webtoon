package com.www.core.file.entity;

import com.www.core.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity

public class Episode extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idx;
	@Column
	private int ep_no;
	@Column
	private String title;
	@Column
	private String author_comment;
	@Column
	private String thumbnail;
	@Column
	private String contents;
	@Column
	private int ep_hits;
	
	@ManyToOne
	@JoinColumn
	private Webtoon webtoon;
	
	//created_date
	//updated_date
	
	@Builder
	public Episode(Webtoon webtoon, String title, int ep_no, String author_comment, 
			String thumbnail, String contents, int ep_hits) {
		//this.idx = idx;
		this.webtoon = webtoon;
		//this.webtoon_idx = webtoon_idx;
		this.title = title;
		this.ep_no = ep_no;
		this.author_comment = author_comment;
		this.thumbnail = thumbnail;
		this.contents = contents;
		this.ep_hits = ep_hits;
		
	}
	

}
