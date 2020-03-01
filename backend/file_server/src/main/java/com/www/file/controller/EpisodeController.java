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

import com.www.core.common.Response;
import com.www.core.common.TokenChecker;
import com.www.file.dto.EpisodeContents;
import com.www.file.dto.EpisodeDto;
import com.www.file.dto.EpisodeListDto;
import com.www.file.dto.EpisodePage;
import com.www.file.service.EpisodeService;
import com.www.file.service.MainService;
import com.www.file.service.WebtoonService;

@RestController
public class EpisodeController {
	@Autowired
	private WebtoonService webtoonService;
	@Autowired
	private EpisodeService episodeService;
	@Autowired
	private MainService mainService;
	
	private TokenChecker tokenchecker = new TokenChecker();
	//회차 등록
	@PostMapping("/myArticleDetail/{idx}")
	public Response<EpisodeDto> addEpisode(@RequestHeader("Authorization") String AccessToken,
			@PathVariable("idx") int idx, @RequestPart("thumbnail") MultipartFile thumbnail, 
			@RequestPart("manuscript") MultipartFile[] manuscripts, @RequestParam("title") String title, 
			@RequestParam("author_comment") String author_comment) throws IllegalStateException, IOException {
		EpisodeDto episodeDto = new EpisodeDto(title, author_comment);
		Response<EpisodeDto> res = new Response<EpisodeDto>();
		int n = tokenchecker.validateToken(AccessToken);
		
		switch(n) {
		case 0: //유효한 토큰
			return episodeService.addEpisode(idx, thumbnail, manuscripts, episodeDto);
		case 1: //만료된 토큰
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: //에러,올바르지 않은 토큰
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;
		
	}

	//회차 정보 수정
	@PutMapping("/myArticleDetail/{webtoon_idx}/{no}")
	public Response<EpisodeDto> editEpisode(@RequestHeader("Authorization") String AccessToken,
			@PathVariable("webtoon_idx") int webtoon_idx, @PathVariable("no") int idx, 
			@RequestPart("thumbnail") MultipartFile thumbnail, @RequestPart("manuscript") MultipartFile[] manuscripts, 
			@RequestParam("title") String title, @RequestParam("author_comment") String author_comment) throws IOException{
		EpisodeDto episodeDto = new EpisodeDto(title, author_comment);
		Response<EpisodeDto> res = new Response<EpisodeDto>();
		int n = tokenchecker.validateToken(AccessToken);
		
		switch(n) {
		case 0: //유효한 토큰
			return episodeService.editEpisode(webtoon_idx, idx, thumbnail, manuscripts, episodeDto);
		case 1: //만료된 토큰
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: //에러,올바르지 않은 토큰
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		return res;
	}
	
	//회차리스트 출력
	@GetMapping("/myArticleList/{idx}")
	public Response<EpisodePage> showEpisodeList(@RequestHeader("Authorization") String AccessToken,
			@PathVariable("idx") int idx, @RequestParam(value="page", defaultValue = "1") Integer page){
		Response<EpisodePage> res = new Response<EpisodePage>();
		EpisodePage episodePage = new EpisodePage();
		
		int n = tokenchecker.validateToken(AccessToken);
		int user_idx = tokenchecker.getUserIdx(AccessToken);
		switch(n) {
		case 0: //유효한 토큰
			List<EpisodeListDto> episodeList = episodeService.getEpisodeList(idx,page,res, episodePage, user_idx);
			Integer[] pageList = episodeService.getPageList(page,idx);
			//EpisodePage episodePage = new EpisodePage(episodeList, pageList);
			episodePage.setEpisodelist(episodeList);
			episodePage.setPagelist(pageList);
			
			switch (res.getCode()) {
			case 0:
				res.setData(episodePage);
				break;
			case 1:
				res.setData(null);
				break;
			}
			return res;
			
		case 1: //만료된 토큰
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: //에러,올바르지 않은 토큰
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;
		
	}
	
	//회차 삭제 
	@DeleteMapping("/myArticleList/{webtoon_idx}/{no}")
	public Response<Integer> deleteEpisode(@RequestHeader("Authorization") String AccessToken,
			@PathVariable("webtoon_idx") int webtoon_idx, @PathVariable("no") int ep_no){
		
		Response<Integer> res = new Response<Integer>();
		int tk = tokenchecker.validateToken(AccessToken);
		int user_idx = tokenchecker.getUserIdx(AccessToken);
		
		switch(tk) {
		case 0: //유효한 토큰
			return episodeService.deleteEpisode(webtoon_idx, ep_no, user_idx);
		case 1: //만료된 토큰
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: //에러,올바르지 않은 토큰
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;
		
		
	}
	
	//기존 회차 정보 가져오기
	@GetMapping("/myArticleDetail/{webtoon_idx}/{no}")
	public Response<EpisodeDto> getOriginalEpisode(@RequestHeader("Authorization") String AccessToken,
			@PathVariable("webtoon_idx") int webtoon_idx, @PathVariable("no") int no) throws IOException{
		Response<EpisodeDto> res = new Response<EpisodeDto>();
		int tk = tokenchecker.validateToken(AccessToken);
		
		switch(tk) {
		case 0: //유효한 토큰
			return episodeService.getEpisodeInfo(webtoon_idx, no);
		case 1: //만료된 토큰
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: //에러,올바르지 않은 토큰
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;		
		
	}
	
	//회차 출력
	@GetMapping("/mydetail/{webtoon_idx}/{no}")
	public Response<EpisodeContents> showMyEpisode(@RequestHeader("Authorization") String AccessToken,
			@PathVariable("webtoon_idx") int webtoon_idx, @PathVariable("no") int no) throws IOException{
		
		Response<EpisodeContents> res = new Response<EpisodeContents>();
		int tk = tokenchecker.validateToken(AccessToken);
		
		switch(tk) {
		case 0: //유효한 토큰
			return mainService.showEpisode(webtoon_idx, no);
		case 1: //만료된 토큰
			res.setCode(40);
			res.setMsg("reissue tokens");
			break;
		case 2: //에러,올바르지 않은 토큰
			res.setCode(42);
			res.setMsg("access denied : maybe captured or faked token");
			break;
		}
		
		return res;		
		
		
	}
		
	
}
