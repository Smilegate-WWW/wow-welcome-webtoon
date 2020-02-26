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
	private LocalDateTime created_date;
	//****별점
	
	@Builder 
	public EpisodeListDto(int idx, int ep_no, String title, String thumbnail, LocalDateTime created_date) {
		this.idx = idx;
		this.ep_no = ep_no;
		this.title = title;
		this.thumnail = thumbnail;
		this.created_date = created_date;
	}
	

}
