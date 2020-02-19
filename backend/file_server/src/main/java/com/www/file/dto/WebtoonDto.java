package com.www.file.dto;

import com.www.core.file.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WebtoonDto {
	//private int idx;
	//private int users_idx;
	private String title;
	private int toon_type;
	private int genre1;
	private int genre2;
	private String summary;
	private String plot;
	private String thumbnail;
	private int end_flag;
	//private LocalDateTime created_date;
	//private LocalDateTime updated_date;
	//private List<Episode> episodes;
	
	public Webtoon toEntity() {
		Webtoon build = Webtoon.builder()
				.title(title)
				.toon_type(toon_type)
				.genre1(genre1)
				.genre2(genre2)
				.summary(summary)
				.plot(plot)
				.thumbnail(thumbnail)
				.end_flag(end_flag)
				.build();
		return build;
	}
	
	/*
	@Builder
	public WebtoonDto(List<Episode> episodes) {
		this.episodes = episodes;
	}
	*/
	

}
