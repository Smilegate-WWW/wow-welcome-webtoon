package com.www.file.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.www.core.common.Response;
import com.www.core.common.TokenChecker;
import com.www.core.file.entity.Episode;
import com.www.core.file.entity.Webtoon;
import com.www.file.dto.EpisodeContents;
import com.www.file.dto.EpisodeDto;
import com.www.file.dto.EpisodeListDto;
import com.www.file.dto.EpisodePage;
import com.www.file.dto.MainWebtoonDto;
import com.www.file.dto.MainWebtoonPage;
import com.www.file.dto.WebtoonListDto;
import com.www.file.dto.WebtoonPage;
import com.www.file.service.EpisodeService;
import com.www.file.service.MainService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class MainController {
	@Autowired
	private MainService mainService;
	@Autowired
	private EpisodeService episodeService;
	
	private TokenChecker tokenChecker;
	
	//웹툰 리스트 출력 (한 페이지당 최대 20개)
	@GetMapping("/webtoonlist")
	public Response<MainWebtoonPage> showWebtoonList(@RequestParam(value="page", defaultValue = "1") Integer page,
			@RequestParam(value="sortBy", defaultValue = "0") int sort ){
		
		Response<MainWebtoonPage> res = new Response<MainWebtoonPage>();
		List<MainWebtoonDto> webtoonList = mainService.getWebtoonList(page,res,sort);
		Integer[] pageList = mainService.getPageList(page);
		MainWebtoonPage webtoonpage = new MainWebtoonPage(webtoonList, pageList);
		switch(res.getCode()) {
		case 0:
			res.setData(webtoonpage);
			break;
		case 1:
			break;
		}
		return res;
	}
	
	
	//회차 리스트 출력
	@GetMapping("/episode/{idx}")
	public Response<EpisodePage> showEpisodeList( @PathVariable("idx") int idx, 
			@RequestParam(value="page", defaultValue = "1") Integer page){
		Response<EpisodePage> res = new Response<EpisodePage>();
		EpisodePage episodePage = new EpisodePage();
		List<EpisodeListDto> episodeList = episodeService.getEpisodeList(idx,page,res,episodePage,-1);
		Integer[] pageList = episodeService.getPageList(page,idx);
		//EpisodePage episodePage = new EpisodePage(episodeList, pageList);
		episodePage.setEpisodelist(episodeList);
		episodePage.setPagelist(pageList);

		switch (res.getCode()) {
		case 0:
			res.setData(episodePage);
			break;
		case 1:
			break;
		}
		return res;
		
	}
	
	//회차 출력
	@GetMapping("/detail/{webtoon_idx}/{no}")
	public Response<EpisodeContents> getOriginalEpisode(@PathVariable("webtoon_idx") int webtoon_idx,
			@PathVariable("no") int no) throws IOException{
		
		return mainService.showEpisode(webtoon_idx, no);
		
	}
			
	
}
