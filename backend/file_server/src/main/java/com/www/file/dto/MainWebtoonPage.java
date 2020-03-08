package com.www.file.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainWebtoonPage {
	private List<MainWebtoonDto> webtoonlist;
	private int totalpage;
	
	public MainWebtoonPage(List<MainWebtoonDto> webtoonlist, int totalpage) {
		this.webtoonlist = webtoonlist;
		this.totalpage = totalpage;
	}
}
