package com.www.file.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.www.file.dto.WebtoonDto;
import com.www.file.dto.WebtoonListDto;
import com.www.file.dto.WebtoonPage;
import com.www.file.service.EpisodeService;
import com.www.file.service.WebtoonService;
import com.www.file.dto.EpisodeDto;
import com.www.file.dto.EpisodeListDto;
import com.www.file.dto.EpisodePage;
import com.www.core.common.Response;
import com.www.core.common.*;

@RestController
public class WebtoonController {
	@Autowired
	private WebtoonService webtoonService;
	@Autowired
	private EpisodeService episodeService;
	
	private TokenChecker tokenchecker = new TokenChecker();
	
	//�떊洹� �쎒�댆 �벑濡�
	@PostMapping("/myTitleDetail")
	public Response<WebtoonDto> registWebtoon(@RequestHeader("Authorization") String AccessToken,
			@RequestPart("thumbnail") MultipartFile file, 
			@RequestPart("webtoon") WebtoonDto webtoonDto) throws IOException {
		Response<WebtoonDto> res = new Response<WebtoonDto>();
		int n = tokenchecker.validateToken(AccessToken);
		
		switch(n) {
		case 0: //�쑀�슚�븳 �넗�겙
			return webtoonService.createWebtoon(file, webtoonDto);
		case 1: //留뚮즺�맂 �넗�겙
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: //�뿉�윭,�삱諛붾Ⅴ吏� �븡�� �넗�겙
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;
	}
	
	//�쎒�댆 由ъ뒪�듃 異쒕젰 (�븳 �럹�씠吏��떦 理쒕� 20媛�)
	@GetMapping("/myTitleList")
	public Response<WebtoonPage> showWebtoonList(@RequestHeader("Authorization") String AccessToken, 
			@RequestParam(value="page", defaultValue = "1") Integer page){
		
		Response<WebtoonPage> res = new Response<WebtoonPage>();
		int n = tokenchecker.validateToken(AccessToken);
		
		switch(n) {
		case 0: //�쑀�슚�븳 �넗�겙
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
			
		case 1: //留뚮즺�맂 �넗�겙
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: //�뿉�윭,�삱諛붾Ⅴ吏� �븡�� �넗�겙
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;
		
		
	}
	
	
	//�쎒�댆 �젙蹂� �닔�젙
	@PutMapping("/myTitleDetail/{idx}")
	public Response<WebtoonDto> editWebtoon(@RequestHeader("Authorization") String AccessToken,
			@PathVariable("idx") int idx, @RequestPart("thumbnail") MultipartFile file, @RequestPart("webtoon") WebtoonDto webtoonDto) throws IOException{
		
		Response<WebtoonDto> res = new Response<WebtoonDto>();
		int n = tokenchecker.validateToken(AccessToken);
		
		switch(n) {
		case 0: //�쑀�슚�븳 �넗�겙
			return webtoonService.editWebtoon(idx, file, webtoonDto);
		case 1: //留뚮즺�맂 �넗�겙
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: //�뿉�윭,�삱諛붾Ⅴ吏� �븡�� �넗�겙
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;
	}
	
	
	//�쎒�댆 �궘�젣 
	@DeleteMapping("/myArticleList/{idx}")
	public Response<Integer> deleteWebtoon(@RequestHeader("Authorization") String AccessToken,
			@PathVariable("idx") int idx){
		
		Response<Integer> res = new Response<Integer>();
		int tk = tokenchecker.validateToken(AccessToken);
		
		switch(tk) {
		case 0: //�쑀�슚�븳 �넗�겙
			return  webtoonService.deleteWebtoon(idx);
		case 1: //留뚮즺�맂 �넗�겙
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: //�뿉�윭,�삱諛붾Ⅴ吏� �븡�� �넗�겙
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;
		
	}

	
	
	

}
