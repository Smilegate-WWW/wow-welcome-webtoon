package com.www.file.dto;

import javax.persistence.Column;

import com.www.file.domain.entity.Episode;
import com.www.file.domain.entity.Webtoon;

public class EpisodeDto {
	
	private int ep_no;
	private String author_comment;
	private String thumnail;
	private int ep_hits;
	
	public Episode toEntity() {
		Episode build = Episode.builder()
				.ep_no(ep_no)
				.author_comment(author_comment)
				.build();
		return build;
		
	}
	

}
