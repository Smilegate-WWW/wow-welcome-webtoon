package com.www.core.file.entity;

import com.www.core.auth.entity.Users;
import com.www.core.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@ToString(exclude = "webtoon_idx")

public class Webtoon extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idx;
	@Column
	private String title;
	@Column
	private int toon_type;
	@Column
	private int genre1;
	@Column
	private int genre2;
	@Column
	private String summary;
	@Column
	private String plot;
	@Column
	private String thumbnail;
	@Column
	private int end_flag;
	@Column(name = "ep_rating_avg")
	private float epRatingAvg;
	@Column 
	private int hits;

	@OneToMany(fetch=FetchType.EAGER,  orphanRemoval = true , cascade = CascadeType.REMOVE, mappedBy = "webtoon")
	//@JoinColumn(name="webtoon_idx")
	private List<Episode> episodes = new ArrayList<Episode>();
	
	@ManyToOne
	@JoinColumn
	private Users users;
	
	@Builder
	public Webtoon(int idx, String title, int toon_type, int genre1,
				   int genre2, String summary, String plot, String thumbnail, int end_flag,
				   Users users, float ep_rating_avg, LocalDateTime created_date, LocalDateTime updated_date) {
		this.idx = idx;
		//this.users_idx = users_idx;
		this.title = title;
		this.toon_type = toon_type;
		this.genre1 = genre1;
		this.genre2 = genre2;
		this.summary = summary;
		this.plot = plot;
		this.thumbnail = thumbnail;
		this.end_flag = end_flag;
		this.epRatingAvg = ep_rating_avg;
		this.users = users;
	}

}
