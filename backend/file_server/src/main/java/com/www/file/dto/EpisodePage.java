package com.www.file.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EpisodePage {
	private List<EpisodeListDto> episodelist;
	private Integer[] pagelist;
	private String webtoon_thumbnail;
	private String webtoon_title;
	private String plot;
	private String writer;
	private String id;
	
	public EpisodePage(List<EpisodeListDto> episodelist, Integer[] pagelist) {
		this.episodelist = episodelist;
		this.pagelist = pagelist;
	}
}
