package com.www.file.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebtoonPage {
	private List<WebtoonListDto> webtoonlist;
	private Integer[] pagelist;
	
	public WebtoonPage(List<WebtoonListDto> webtoonlist, Integer[] pagelist) {
		this.webtoonlist = webtoonlist;
		this.pagelist = pagelist;
	}
}
