package com.www.file.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EpisodePage {
	private List<EpisodeListDto> episodelist;
	private Integer[] pagelist;
	
	public EpisodePage(List<EpisodeListDto> episodelist, Integer[] pagelist) {
		this.episodelist = episodelist;
		this.pagelist = pagelist;
	}
}
