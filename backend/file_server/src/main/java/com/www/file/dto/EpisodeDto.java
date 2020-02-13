package com.www.file.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;

import com.www.file.domain.entity.Episode;
import com.www.file.domain.entity.Webtoon;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
public class EpisodeDto {
	
	private int ep_no;
	private String title;
	private String author_comment;
	private String thumbnail;
	private String contents;
	//private int ep_hits;
	//private Webtoon webtoon;
	
	public Episode toEntity() {
		Episode build = Episode.builder()
				.ep_no(ep_no)
				.title(title)
				.author_comment(author_comment)
				.thumbnail(thumbnail)
				.contents(contents)
				//.webtoon(webtoon)
				.build();
		return build;
	}
	public EpisodeDto(EpisodeDto p) {
		this.ep_no = ep_no;
		this.title = title;
		this.author_comment = author_comment;
		this.thumbnail = thumbnail;
		this.contents = contents;
	}
	@Builder
	public EpisodeDto(int ep_no, String title, String author_comment, String thumbnail,
			String contents) {
		this.ep_no = ep_no;
		this.title = title;
		this.author_comment = author_comment;
		this.thumbnail = thumbnail;
		this.contents = contents;
	}
	
	

}
