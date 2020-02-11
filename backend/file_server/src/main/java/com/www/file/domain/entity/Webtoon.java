package com.www.file.domain.entity;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
@Entity
/*
@SequenceGenerator(
		name = "WEBTOON_SEQ_GENERATOR",
		sequenceName = "WEBTOON_SEQ" ,
		initialValue = 1, allocationSize = 1
)
*/
public class Webtoon extends TimeEntity{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idx;
	@Column
	private int users_idx;
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
	
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "webtoon")
	//@JoinColumn(name="webtoon_idx")
	private List<Episode> episodes = new ArrayList<Episode>();

	
	//created_date
	//updated_date
	
	@Builder
	public Webtoon(int idx, /*int users_idx,*/ String title, int toon_type, int genre1, 
			int genre2, String summary, String plot, String thumbnail, int end_flag) {
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
	}

}
