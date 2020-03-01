package com.www.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class EpisodeContents {
	
	private String title;
	private String author;
	private String summary;
	private String thumbnail;
	
	private float rating_avg;
	private int ep_hits;
	private String[] contents;
	
	@Builder
	public EpisodeContents(String title, String author, String summary, String thumbnail, 
			float rating_avg, int ep_hits) {
		this.title = title;
		this.author = author;
		this.summary = summary;
		this.thumbnail = thumbnail;
		this.rating_avg = rating_avg;
		this.ep_hits = ep_hits;
	}
}
