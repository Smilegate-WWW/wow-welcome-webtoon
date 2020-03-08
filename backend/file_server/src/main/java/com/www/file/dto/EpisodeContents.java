package com.www.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class EpisodeContents {
	
	private String webtoon_title;
	private String title;
	private String author;
	private String summary;
	private String thumbnail;
	private String author_comment;
	private int rating_person_total;
	private float rating_avg;
	private int ep_hits;
	private String[] contents;
	
	@Builder
	public EpisodeContents(String webtoon_title, String title, String author, String summary, String thumbnail, 
			String author_comment, int rating_person_total, float rating_avg, int ep_hits) {
		this.webtoon_title = webtoon_title;
		this.title = title;
		this.author_comment = author_comment;
		this.author = author;
		this.summary = summary;
		this.thumbnail = thumbnail;
		this.rating_person_total = rating_person_total;
		this.rating_avg = rating_avg;
		this.ep_hits = ep_hits;
	}
}
