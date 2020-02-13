package com.www.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.www.file.domain.repository.WebtoonRepository;
import com.www.file.dto.WebtoonDto;
import com.www.file.service.EpisodeService;
import com.www.file.service.WebtoonService;
import com.www.file.dto.EpisodeDto;
import com.www.file.dto.EpisodeListDto;
import com.www.file.dto.Response;

@RestController
public class WebtoonController {
	@Autowired
	private WebtoonRepository webtoonRepository;
	//@Autowired
	//private FileUploadService service;
	@Autowired
	private WebtoonService webtoonService;
	
	@Autowired
	private EpisodeService episodeService;
	
	/*
	@PostMapping("/fileTest")
	public void postFile(@RequestParam("file") MultipartFile file) throws IOException {
		webtoonService.postImage(file);
	}
	*/
	
	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	//신규 웹툰 등록
	@PostMapping("/myTitleDetail")
	public Response<WebtoonDto> registWebtoon(@RequestPart("thumbnail") MultipartFile file, @RequestPart("webtoon") WebtoonDto webtoonDto) throws IOException {
		Response<WebtoonDto> res = new Response<WebtoonDto>();
		res.setCode(webtoonService.createWebtoon(file, webtoonDto));
		switch (res.getCode()) {
		case 0:
			res.setMsg("insert complete");
			res.setData(webtoonDto);
			break;
		case 1:
			res.setMsg("insert fail");
			break;
		}
		return res;
		
	}
	
	//회차 리스트 
	@GetMapping("/myArticleList/{idx}")
	public Response<List<EpisodeListDto>> showlist(@PathVariable("idx") int idx){
	
		List<EpisodeListDto> episodeList = episodeService.getEpisodeList(idx);
		Response<List<EpisodeListDto>> res = new Response<List<EpisodeListDto>>();
		res.setData(episodeList);
		res.setCode(0);
		res.setMsg("show complete");
		return res;
	}
	
	//작품 정보 수정 
	@PutMapping("/myTitleDetail/{idx}")
	public Response<WebtoonDto> editWebtoon(@PathVariable("idx") int idx, @RequestPart("thumbnail") MultipartFile file, @RequestPart("webtoon") WebtoonDto webtoonDto) throws IOException{
		Response<WebtoonDto> res = new Response<WebtoonDto>();
		res.setCode(webtoonService.editWebtoon(idx, file, webtoonDto));
		switch (res.getCode()) {
		case 0:
			res.setMsg("modify complete");
			res.setData(webtoonDto);
			break;
		case 1:
			res.setMsg("insert fail");
			break;
		}
		return res;
	}
	
	//해당 idx의 웹툰에 신규 회차 등록
	@PostMapping("/myArticleDetail/{idx}")
	public Response<EpisodeDto> addEpisode(@PathVariable("idx") int idx, @RequestPart("thumbnail") MultipartFile thumbnail, 
			@RequestPart("manuscript") MultipartFile[] manuscripts, @RequestPart("episode") EpisodeDto episodeDto) {
		Response<EpisodeDto> res = new Response<EpisodeDto>();
		res.setCode(episodeService.addEpisode(idx, thumbnail, manuscripts, episodeDto));
		switch (res.getCode()) {
		case 0:
			res.setMsg("insert complete");
			res.setData(episodeDto);
			break;
		case 1:
			res.setMsg("insert fail");
			break;
		}
		return res;
		
	}
	
	@PutMapping("/myArticleDetail/{idx}")
	public Response<EpisodeDto> editEpisode(@PathVariable("idx") int idx, @RequestPart("thumbnail") MultipartFile thumbnail, 
			@RequestPart("manuscript") MultipartFile[] manuscripts, @RequestPart("episode") EpisodeDto episodeDto) throws IOException{
		Response<EpisodeDto> res = new Response<EpisodeDto>();
		res.setCode(episodeService.editEpisode(idx, thumbnail, manuscripts, episodeDto));
		switch (res.getCode()) {
		case 0:
			res.setMsg("modify complete");
			res.setData(episodeDto);
			break;
		case 1:
			res.setMsg("insert fail");
			break;
		}
		return res;
	}

}
