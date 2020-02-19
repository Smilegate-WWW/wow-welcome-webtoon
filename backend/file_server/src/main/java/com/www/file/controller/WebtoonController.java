package com.www.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.www.core.file.repository.*;
import com.www.file.dto.WebtoonDto;
import com.www.file.dto.WebtoonListDto;
import com.www.file.dto.WebtoonPage;
import com.www.file.service.EpisodeService;
import com.www.file.service.WebtoonService;
import com.www.file.dto.EpisodeDto;
import com.www.file.dto.EpisodeListDto;
import com.www.file.dto.EpisodePage;
import com.www.core.common.Response;

@RestController
public class WebtoonController {
	@Autowired
	private WebtoonRepository webtoonRepository;
	
	@Autowired
	private WebtoonService webtoonService;
	
	@Autowired
	private EpisodeService episodeService;
	
	//신규 웹툰 등록
	//예외처리 : 필수 입력 조건 체크
	@PostMapping("/myTitleDetail")
	public Response<WebtoonDto> registWebtoon(@RequestHeader("Authorization") String AccessToken,
			@RequestPart("thumbnail") MultipartFile file, 
			@RequestPart("webtoon") WebtoonDto webtoonDto) throws IOException {
		//System.out.println("실행중");
		
		return webtoonService.createWebtoon(file, webtoonDto);
		
	}
	
	//웹툰 목록 페이징하여 출력 (한 페이지당 20개)
	//예외 처리 : 유효한 페이지 체크
	@GetMapping("/myTitleList")
	public Response<WebtoonPage> showWebtoonList(@RequestParam(value="page", defaultValue = "1") Integer page){
		Response<WebtoonPage> res = new Response<WebtoonPage>();
		List<WebtoonListDto> webtoonList = webtoonService.getWebtoonList(page,res);
		Integer[] pageList = webtoonService.getPageList(page);
		WebtoonPage webtoonpage = new WebtoonPage(webtoonList, pageList);

		switch(res.getCode()) {
		case 0:
			res.setData(webtoonpage);
			break;
		case 1:
			break;
		}
		return res;
	}
	
	
	//웹툰 삭제
	//예외 처리: 삭제하고자 하는 웹툰 유효여부 체크 
	@DeleteMapping("/myArticleList/{idx}")
	public Response<Integer> deleteWebtoon(@PathVariable("idx") int idx){
		int n = webtoonService.deleteWebtoon(idx);
		Response<Integer> res = new Response<Integer>();
		
		switch (n) {
		case 0:
			res.setMsg("delete complete");
			break;
		case 1:
			res.setMsg("delete fail. Webtoon do not exists");
			break;
		}
		return res;
	}
	
	//작품 정보 수정 
	//예외 처리 완료: 필수 조건 체크 
	@PutMapping("/myTitleDetail/{idx}")
	public Response<WebtoonDto> editWebtoon(@PathVariable("idx") int idx, @RequestPart("thumbnail") MultipartFile file, @RequestPart("webtoon") WebtoonDto webtoonDto) throws IOException{
		return webtoonService.editWebtoon(idx, file, webtoonDto);
	}
	
	//회차 삭제
	//예외 처리: 삭제하고자 하는 회차 유효여부 체크 
	@DeleteMapping("/myArticleList/{webtoon_idx}/{no}")
	public Response<Integer> deleteEpisode(@PathVariable("webtoon_idx") int webtoon_idx, @PathVariable("no") int ep_no){
		int n = episodeService.deleteEpisode(webtoon_idx, ep_no);
		Response<Integer> res = new Response<Integer>();
		
		switch (n) {
		case 0:
			res.setMsg("delete complete");
			break;
		case 1:
			res.setMsg("delete fail. Episode do not exists");
			break;
		}
		return res;
	}
	
	//회차 리스트  페이징하여 출력 (한 페이지당 7개)
	//예외 처리 : 유효한 페이지 체크
	@GetMapping("/myArticleList/{idx}")
	public Response<EpisodePage> showEpisodeList(@PathVariable("idx") int idx, 
			@RequestParam(value="page", defaultValue = "1") Integer page){
		Response<EpisodePage> res = new Response<EpisodePage>();
		List<EpisodeListDto> episodeList = episodeService.getEpisodeList(idx,page,res);
		Integer[] pageList = episodeService.getPageList(page,idx);
		EpisodePage episodePage = new EpisodePage(episodeList, pageList);

		switch (res.getCode()) {
		case 0:
			res.setData(episodePage);
			break;
		case 1:
			break;
		}
		return res;
	}
	
	
	//해당 idx의 웹툰에 신규 회차 등록
	//예외처리: 필수 조건 체크 
	@PostMapping("/myArticleDetail/{idx}")
	public Response<EpisodeDto> addEpisode(@PathVariable("idx") int idx, @RequestPart("thumbnail") MultipartFile thumbnail, 
			@RequestPart("manuscript") MultipartFile[] manuscripts, @RequestPart("episode") EpisodeDto episodeDto) {
		return episodeService.addEpisode(idx, thumbnail, manuscripts, episodeDto);
		
	}
	
	//회차 수정
	//예외처리: 필수 조건 체크 
	@PutMapping("/myArticleDetail/{webtoon_idx}/{no}")
	public Response<EpisodeDto> editEpisode(@PathVariable("webtoon_idx") int webtoon_idx, @PathVariable("no") int idx, 
			@RequestPart("thumbnail") MultipartFile thumbnail, @RequestPart("manuscript") MultipartFile[] manuscripts, 
			@RequestPart("episode") EpisodeDto episodeDto) throws IOException{
		return episodeService.editEpisode(webtoon_idx, idx, thumbnail, manuscripts, episodeDto);
	}

}
