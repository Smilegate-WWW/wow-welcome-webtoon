package com.www.file.dto;

import java.time.LocalDateTime;

import com.www.core.auth.entity.Users;
import com.www.file.dto.WebtoonListDto.WebtoonListDtoBuilder;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MainWebtoonDto {
	
	private int idx;
	private String title;
	private String author;
	private String thumbnail;
	private int genre1;
	private int genre2;
	private int hits;
	private float epRatingAvg;
	private Integer[] pagelist;
	
	@Builder
	public MainWebtoonDto(int idx, String author, String title, String thumbnail, int genre1, int genre2, float epRatingAvg, int hits) {
		this.idx = idx;
		this.title = title;
		this.author = author;
		this.thumbnail = thumbnail;
		this.genre1 = genre1;
		this.genre2 = genre2;
		this.epRatingAvg = epRatingAvg;
		this.hits = hits;
	}
}
