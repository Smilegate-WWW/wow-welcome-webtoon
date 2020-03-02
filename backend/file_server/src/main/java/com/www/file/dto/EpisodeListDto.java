package com.www.file.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EpisodeListDto {
	
	private int idx;
	private int ep_no;
	private String title;
	private String thumnail;
	private String author_comment;
	private LocalDateTime created_date;
	private float rating_avg;
	
	@Builder 
	public EpisodeListDto(int idx, int ep_no, String title, String thumbnail, 
			String author_comment, float rating_avg, LocalDateTime created_date) {
		this.idx = idx;
		this.ep_no = ep_no;
		this.title = title;
		this.thumnail = thumbnail;
		this.author_comment = author_comment;
		this.rating_avg = rating_avg;
		this.created_date = created_date;
	}
	

}
