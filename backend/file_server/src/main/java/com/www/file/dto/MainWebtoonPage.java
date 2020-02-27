package com.www.file.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainWebtoonPage {
	private List<MainWebtoonDto> webtoonlist;
	private Integer[] pagelist;
	
	public MainWebtoonPage(List<MainWebtoonDto> webtoonlist, Integer[] pagelist) {
		this.webtoonlist = webtoonlist;
		this.pagelist = pagelist;
	}
}
