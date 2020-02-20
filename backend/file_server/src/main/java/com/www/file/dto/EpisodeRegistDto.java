package com.www.file.dto;
import com.www.core.file.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class EpisodeRegistDto extends EpisodeDto {
	private Webtoon webtoon;
	
	public EpisodeRegistDto(EpisodeDto p, Webtoon webtoon) {
		setAuthor_comment(p.getAuthor_comment());
		setContents(p.getContents());
		setEp_no(p.getEp_no());
		setThumbnail(p.getThumbnail());
		setTitle(p.getTitle());
		this.webtoon = webtoon;
	}
	public Episode toEntity() {
		Episode build = Episode.builder()
				.ep_no(getEp_no())
				.title(getTitle())
				.author_comment(getAuthor_comment())
				.thumbnail(getThumbnail())
				.contents(getContents())
				.webtoon(webtoon)
				.build();
		return build;
	}
	
	

}
