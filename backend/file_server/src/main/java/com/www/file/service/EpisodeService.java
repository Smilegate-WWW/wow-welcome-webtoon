package com.www.file.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.www.core.file.entity.*;
import com.www.core.file.repository.*;
import com.www.file.dto.EpisodeDto;
import com.www.file.dto.EpisodeListDto;
import com.www.file.dto.EpisodePage;
import com.www.file.dto.EpisodeRegistDto;
import com.www.core.common.Response;


@Service
public class EpisodeService {
	
	private WebtoonRepository webtoonRepository;
	private EpisodeRepository episodeRepository;
	
	//한 블럭 내 최대 페이지 번호 수
	private static final int BLOCK_PAGE_NUM_COUNT = 5;
	//한 페이지 내 최대 회차 출력 갯수
	private static final int PAGE_EPISODE_COUNT = 7;
		
	@Value("${custom.path.upload-images}")
	private String filePath;
	
	public EpisodeService(WebtoonRepository webtoonRepository, EpisodeRepository episodeRepository) {
		this.webtoonRepository = webtoonRepository;
		this.episodeRepository = episodeRepository;
	}
	//필수 조건 체크
	public void checkCondition(MultipartFile thumbnail, MultipartFile[] manuscript, EpisodeDto episodeDto, Response<EpisodeDto> res) {
		
		if(episodeDto.getTitle()==null) {
			res.setCode(10);
			res.setMsg("insert fail: need to register title");
			return;
		}
		
		if(thumbnail.isEmpty()) {
			res.setCode(13);
			res.setMsg("insert fail: need to register thumbnail");
			return;
		}
		
		if(episodeDto.getAuthor_comment()==null) {
			res.setCode(15);
			res.setMsg("insert fail: need to register author_comment");
			return;
		}
		
		res.setCode(0);
		res.setMsg("insert complete");
	}
	
	@Transactional
	public Response<EpisodePage> getEpisodeList(int idx, Integer pageNum, int user_idx) {
		
		Response<EpisodePage> res = new Response<EpisodePage>();
		EpisodePage episodePage = new EpisodePage();
		Pageable pageable = PageRequest.of(pageNum-1, PAGE_EPISODE_COUNT);
		Page<Episode> page = episodeRepository.findAllByWebtoonIdx(pageable,idx);		
	    List<EpisodeListDto> episodeDtoList = new ArrayList<>();
	 
	    int totalpages = page.getTotalPages();
	    if(totalpages==0) totalpages=1;
	    System.out.println("*****회차 목록 출력 idx 체크 : "+ idx);
	    ///////////////////////////////////////
	    episodePage.setTotalpage(totalpages);
	    if(!webtoonRepository.existsById(idx)) {
	    	System.out.println("존재하지 않음");
	    	
	    }
	    else {
	    	//웹툰 정보 기입
		    Optional<Webtoon> WebtoonEntityWrapper = webtoonRepository.findById(idx);
	        Webtoon webtoon = WebtoonEntityWrapper.get();
	        
	        if(webtoon.getUsers().getIdx()!=user_idx && user_idx!=-1) {
	        	System.out.println("작가 일치 X");
	        	res.setCode(1);
	        	res.setMsg("fail: user do not match");
	        	return res;
	        }
	        episodePage.setWebtoon_title(webtoon.getTitle());
	        episodePage.setPlot(webtoon.getPlot());
	        episodePage.setId(webtoon.getUsers().getUserid());
	        episodePage.setWriter(webtoon.getUsers().getName());
	        episodePage.setWebtoon_thumbnail("http://localhost:8081/static/web_thumbnail/"+webtoon.getThumbnail());
	        System.out.println("5");
	    }
	    /////////////////////////////////////////////
	    //요청한 페이지 번호가 유효한 범위인지 체크
	    if(pageNum>0 && pageNum<=totalpages) {
	    	List<Episode> episodeList = page.getContent();
		    for(Episode episode : episodeList) {
		    	EpisodeListDto episodeDto = EpisodeListDto.builder()
		    			.idx(episode.getIdx())
		    			.ep_no(episode.getEp_no())
		    			.title(episode.getTitle())
		    			.rating_avg(episode.getRating_avg())
		    			.thumbnail("http://localhost:8081/static/ep_thumbnail/"+episode.getThumbnail())
		    			.author_comment(episode.getAuthor_comment())
		    			.created_date(episode.getCreated_date())
		    			.build();
		    	episodeDtoList.add(episodeDto);
		    }
		    episodePage.setEpisodelist(episodeDtoList);
		    res.setData(episodePage);
		    res.setCode(0);
		    res.setMsg("show complete");
	    }
	    
	    else {
	    	res.setCode(1);
	    	res.setMsg("fail : pageNum is not in valid range");
	    }
	    
	    return res;
	    
	}
	
	
	@Transactional
	public Response<EpisodeDto> addEpisode(int webtoon_idx, MultipartFile thumbnail, MultipartFile[] manuscripts, EpisodeDto episodeDto) throws IllegalStateException, IOException {
		
		Response<EpisodeDto> res = new Response<EpisodeDto>();
		
		//유효한 웹툰 idx가 아닌경우 
		if(!webtoonRepository.existsById(webtoon_idx)) {
			res.setCode(1);
			res.setMsg("fail: Webtoon do not exists");
			return res;
        }
		
		checkCondition(thumbnail, manuscripts, episodeDto, res);
		Optional<Webtoon> WebtoonEntityWrapper = webtoonRepository.findById(webtoon_idx);
        Webtoon webtoon = WebtoonEntityWrapper.get();
        
        int lastno;
        List<Episode> episodeList = webtoon.getEpisodes();
        
        //첫 회차 등록이 아닐 시 가장 마지막 회차 번호 +1
        if(!episodeList.isEmpty()) {
        	Episode e = episodeList.get(episodeList.size()-1);
        	lastno = e.getEp_no();
        	episodeDto.setEp_no(lastno+1);
        }
        
        //첫 회차 등록시
        else {
        	episodeDto.setEp_no(1);
        }
         
        
        String thumbnailName = thumbnail.getOriginalFilename();
        episodeDto.setThumbnail(thumbnailName);
        
        File ThumbDestinationFile = new File(filePath+"/ep_thumbnail/"+thumbnailName);
        ThumbDestinationFile.getParentFile().mkdir();
		thumbnail.transferTo(ThumbDestinationFile);
		
        System.out.println(manuscripts.length);
        String manuscriptsName="";
        for(int i=0;i<manuscripts.length;i++) {
        	if(i!=0) manuscriptsName+=";";
        	UUID uuid = UUID.randomUUID();
        	manuscriptsName += uuid + "_" + manuscripts[i].getOriginalFilename();
        }
        
        episodeDto.setContents(manuscriptsName);
        
		//file 외부 폴더로 이동
		for(int i=0;i<manuscripts.length;i++) {
			File destinationFile = new File(filePath+"/webtoon/"+manuscripts[i].getOriginalFilename());
			destinationFile.getParentFile().mkdir();
        	manuscripts[i].transferTo(destinationFile);
        }
        
        EpisodeRegistDto ep = new EpisodeRegistDto(episodeDto,webtoon);
      
		episodeRepository.save(ep.toEntity());
		res.setData(episodeDto);
		return res;
	}
	
	public Response<EpisodeDto> editEpisode(int webtoon_idx,int no, MultipartFile thumbnail, MultipartFile[] manuscripts, EpisodeDto episodeDto) throws IllegalStateException, IOException {
		
		Response<EpisodeDto> res = new Response<EpisodeDto>();
		Optional<Webtoon> webtoonWrapper = webtoonRepository.findById(webtoon_idx);
		Webtoon webtoon = webtoonWrapper.get();
		List<Episode> epList = webtoon.getEpisodes();
		Episode episode = new Episode();
		
		for(Episode ep : epList) {
			if(no == ep.getEp_no()) {
				episode = ep;
				break;
			}
		}
		
		//유효한 에피소드가 아닐 시 
		if(episode.getTitle()==null) {
			res.setCode(1);
      		res.setMsg("fail: Episode do not exists");
      		return res;
		}
		
        checkCondition(thumbnail, manuscripts, episodeDto, res);
        episode.setAuthor_comment(episodeDto.getAuthor_comment());
        episode.setTitle(episodeDto.getTitle());
        
        String thumbnailName = thumbnail.getOriginalFilename();
        episodeDto.setThumbnail(thumbnailName);
        episode.setThumbnail(thumbnailName);
        
        File ThumbDestinationFile = new File(filePath+"/ep_thumbnail/"+thumbnailName);
        ThumbDestinationFile.getParentFile().mkdir();
		thumbnail.transferTo(ThumbDestinationFile);
		
        String manuscriptsName="";
        for(int i=0;i<manuscripts.length;i++) {
        	if(i!=0) manuscriptsName+=";";
        	UUID uuid = UUID.randomUUID();
        	manuscriptsName += uuid + "_" + manuscripts[i].getOriginalFilename();
        }
        
        episodeDto.setContents(manuscriptsName);
        episode.setContents(manuscriptsName);
        
        //file 외부 폴더로 이동
      	for(int i=0;i<manuscripts.length;i++) {
      		File destinationFile = new File(filePath+"/webtoon/"+manuscripts[i].getOriginalFilename());
      		destinationFile.getParentFile().mkdir();
            manuscripts[i].transferTo(destinationFile);
        }
        
        episodeRepository.save(episode);
        res.setData(episodeDto);
        return res;
	}
	
	public Response<Integer> deleteEpisode(int webtoon_idx, int ep_no, int user_idx) {
		Response<Integer> res = new Response<Integer>();
		Optional<Webtoon> webtoonWrapper = webtoonRepository.findById(webtoon_idx);
		Webtoon webtoon = webtoonWrapper.get();
		if(webtoon.getUsers().getIdx() != user_idx) {
			res.setCode(1);
			res.setMsg("delete fail: user do not have authority");
			return res;
		}
		List<Episode> epList = webtoon.getEpisodes();
		Episode episode = new Episode();
		
		for(Episode ep : epList) {
			if(ep_no == ep.getEp_no()) {
				episode = ep;
				break;
			}
		}
		
		//유효한 에피소드가 아닐 시 
		if(episode.getTitle()==null) {
			res.setCode(1);
			res.setMsg("delete fail. Episode do not exists");
		}
		 
        else {
            episodeRepository.delete(episode);
            res.setMsg("delete complete");
            res.setCode(0);
        }
		
		return res;
        
	}

	public Response<EpisodeDto> getEpisodeInfo(int webtoon_idx, int no){
		
		Response<EpisodeDto> res = new Response<EpisodeDto>();
		
		Optional<Webtoon> webtoonWrapper = webtoonRepository.findById(webtoon_idx);
		Webtoon webtoon = webtoonWrapper.get();
		List<Episode> epList = webtoon.getEpisodes();
		Episode episode = new Episode();
		
		for(Episode ep : epList) {
			if(no == ep.getEp_no()) {
				episode = ep;
				break;
			}
		}
		
		EpisodeDto episodeDto = EpisodeDto.builder()
				.ep_no(episode.getEp_no())
				.title(episode.getTitle())
				.author_comment(episode.getAuthor_comment())
				.thumbnail(episode.getThumbnail())
				.contents(episode.getContents())
				.build();
		
		res.setData(episodeDto);
		res.setCode(0);
		res.setMsg("get episode info");
		return res;
		
	}

}
