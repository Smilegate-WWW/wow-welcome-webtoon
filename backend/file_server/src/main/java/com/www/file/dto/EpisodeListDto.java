package com.www.file.dto;

import java.time.LocalDateTime;
import com.www.core.file.entity.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EpisodeListDto {
	
	private int ep_no;
	private String title;
	private String thumnail;
	private LocalDateTime created_date;
	
	@Builder 
	public EpisodeListDto(int ep_no, String title, String thumbnail, LocalDateTime created_date) {
		this.ep_no = ep_no;
		this.title = title;
		this.thumnail = thumbnail;
		this.created_date = created_date;
	}
	

}
