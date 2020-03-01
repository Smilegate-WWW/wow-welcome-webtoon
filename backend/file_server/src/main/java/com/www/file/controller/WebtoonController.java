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

import lombok.AllArgsConstructor;

import com.www.file.dto.EpisodeDto;
import com.www.file.dto.EpisodeListDto;
import com.www.file.dto.EpisodePage;
import com.www.core.common.*;

@RestController
@AllArgsConstructor
public class WebtoonController {
	@Autowired
	private WebtoonService webtoonService;
	@Autowired
	private EpisodeService episodeService;

	private TokenChecker tokenChecker;
	
	//새 웹툰 등록
	@PostMapping("/myTitleDetail")
	public Response<WebtoonDto> createWebtoon(@RequestHeader("Authorization") String AccessToken,
			@RequestPart("thumbnail") MultipartFile file, @RequestParam("title") String title, @RequestParam("toon_type") int toon_type, 
			@RequestParam("genre1") int genre1, @RequestParam("genre2") int genre2, @RequestParam("summary") String summary,
			@RequestParam("plot") String plot, @RequestParam("end_flag") int end_flag) throws IOException {
		WebtoonDto webtoonDto = new WebtoonDto(title,toon_type,genre1,genre2,summary,plot,end_flag);
		Response<WebtoonDto> res = new Response<WebtoonDto>();
		int n = tokenChecker.validateToken(AccessToken);
		int user_idx = tokenChecker.getUserIdx(AccessToken);
		switch(n) {
		case 0: //유효한 토큰
			return webtoonService.createWebtoon(file, webtoonDto, user_idx);
		case 1: //만료된 토큰
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: //유효하지 않은 토큰
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;
	}
	
	//내 웹툰 리스트 출력 (한 페이지당 최대 20개)
	@GetMapping("/myTitleList")
	public Response<WebtoonPage> showWebtoonList(@RequestHeader("Authorization") String AccessToken, 
			@RequestParam(value="page", defaultValue = "1") Integer page){
		
		Response<WebtoonPage> res = new Response<WebtoonPage>();
		int n = tokenChecker.validateToken(AccessToken);
		int user_idx = tokenChecker.getUserIdx(AccessToken);
		
		switch(n) {
		case 0: //유효한 토큰
			List<WebtoonListDto> webtoonList = webtoonService.getWebtoonList(page,res,user_idx);
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
			
		case 1: 
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: 
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;
	}
	
	//내 웹툰 정보 수정
	@PutMapping("/myTitleDetail/{idx}")
	public Response<WebtoonDto> editWebtoon(@RequestHeader("Authorization") String AccessToken, @PathVariable("idx") int idx,
			@RequestPart("thumbnail") MultipartFile file, @RequestParam("title") String title, @RequestParam("toon_type") int toon_type, 
			@RequestParam("genre1") int genre1, @RequestParam("genre2") int genre2, @RequestParam("summary") String summary,
			@RequestParam("plot") String plot, @RequestParam("end_flag") int end_flag) throws IOException {
		
		WebtoonDto webtoonDto = new WebtoonDto(title,toon_type,genre1,genre2,summary,plot,end_flag);
		Response<WebtoonDto> res = new Response<WebtoonDto>();
		int n = tokenChecker.validateToken(AccessToken);
		
		switch(n) {
		case 0: 
			return webtoonService.editWebtoon(idx, file, webtoonDto);
		case 1: 
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: 
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;
	}
	
	//기존 웹툰 정보 가져오기 
	@GetMapping("/myTitleDetail/{idx}")
	public Response<WebtoonDto> GetOriginalWebtoon(@RequestHeader("Authorization") String AccessToken, @PathVariable("idx") int idx) throws IOException {
		
		Response<WebtoonDto> res = new Response<WebtoonDto>();
		int n = tokenChecker.validateToken(AccessToken);
		
		switch(n) {
		case 0: 
			return webtoonService.getWebtoonInfo(idx);
		case 1: 
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: 
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;
	}
	//내 웹툰 삭제 
	@DeleteMapping("/myArticleList/{idx}")
	public Response<Integer> deleteWebtoon(@RequestHeader("Authorization") String AccessToken,
			@PathVariable("idx") int idx){
		
		Response<Integer> res = new Response<Integer>();
		int tk = tokenChecker.validateToken(AccessToken);
		int user_idx = tokenChecker.getUserIdx(AccessToken);
		switch(tk) {
		case 0: 
			return  webtoonService.deleteWebtoon(idx, user_idx);
		case 1:
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: 
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;
		
	}

	
	
	

}
