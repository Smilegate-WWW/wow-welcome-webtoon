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
	
	private String title;
	private String thumbnail;
	private int genre1;
	private int genre2;
	//별점 ********
	private Integer[] pagelist;
	
	@Builder
	public MainWebtoonDto(String title, String thumbnail, int genre1, int genre2) {
		this.title = title;
		this.thumbnail = thumbnail;
		this.genre1 = genre1;
		this.genre2 = genre2;
	}
}
