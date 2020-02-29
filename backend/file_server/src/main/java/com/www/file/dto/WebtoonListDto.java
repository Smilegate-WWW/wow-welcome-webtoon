package com.www.file.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WebtoonListDto {
	
	private int idx;
	private String title;
	private String thumbnail;
	private LocalDateTime created_date;
	private LocalDateTime last_updated;
	
	@Builder
	public WebtoonListDto(int idx, String title, String thumbnail, LocalDateTime created_date, LocalDateTime last_updated) {
		this.idx = idx;
		this.title = title;
		this.thumbnail = thumbnail;
		this.created_date = created_date;
		this.last_updated = last_updated;
	}
}
