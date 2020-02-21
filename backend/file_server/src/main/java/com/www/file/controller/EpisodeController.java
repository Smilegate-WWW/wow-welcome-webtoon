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
import com.www.file.dto.EpisodeDto;
import com.www.file.dto.EpisodeListDto;
import com.www.file.dto.EpisodePage;
import com.www.file.service.EpisodeService;
import com.www.file.service.WebtoonService;

@RestController
public class EpisodeController {
	@Autowired
	private WebtoonService webtoonService;
	@Autowired
	private EpisodeService episodeService;
	
	private TokenChecker tokenchecker = new TokenChecker();

	//회차 등록
	@PostMapping("/myArticleDetail/{idx}")
	public Response<EpisodeDto> addEpisode(@RequestHeader("Authorization") String AccessToken,
			@PathVariable("idx") int idx, @RequestPart("thumbnail") MultipartFile thumbnail, 
			@RequestPart("manuscript") MultipartFile[] manuscripts, @RequestPart("episode") EpisodeDto episodeDto) {

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
			@RequestPart("episode") EpisodeDto episodeDto) throws IOException{
		
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
		
		int n = tokenchecker.validateToken(AccessToken);
		
		switch(n) {
		case 0: //유효한 토큰
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
		
		switch(tk) {
		case 0: //유효한 토큰
			return episodeService.deleteEpisode(webtoon_idx, ep_no);
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