package com.www.file.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import com.www.file.domain.entity.Episode;
import com.www.file.domain.entity.Webtoon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class EpisodeDto {
	
	private int ep_no;
	private String author_comment;
	private String thumnail;
	private String contents;
	//private int ep_hits;
	private Webtoon webtoon;
	
	public Episode toEntity() {
		Episode build = Episode.builder()
				.ep_no(ep_no)
				.author_comment(author_comment)
				.thumnail(thumnail)
				.contents(contents)
				.webtoon(webtoon)
				.build();
		return build;
		
	}
	

}
