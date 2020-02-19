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
	private String title;
	private LocalDateTime created_date;
	private LocalDateTime last_updated;
	
	@Builder
	public WebtoonListDto(String title, LocalDateTime created_date, LocalDateTime last_updated) {
		this.title = title;
		this.created_date = created_date;
		this.last_updated = last_updated;
	}
}
