package com.www.file.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebtoonPage {
	private List<WebtoonListDto> webtoonlist;
	private int totalpage;
	
	public WebtoonPage(List<WebtoonListDto> webtoonlist, int totalpage) {
		this.webtoonlist = webtoonlist;
		this.totalpage = totalpage;
	}
}
