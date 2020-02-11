package com.www.file.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.www.file.domain.entity.Episode;
import com.www.file.domain.entity.Webtoon;
import com.www.file.domain.repository.EpisodeRepository;
import com.www.file.domain.repository.WebtoonRepository;
import com.www.file.dto.EpisodeDto;
import com.www.file.dto.EpisodeListDto;
import com.www.file.dto.EpisodeRegistDto;
import com.www.file.dto.WebtoonDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EpisodeService {
	
	private WebtoonRepository webtoonRepository;
	private EpisodeRepository episodeRepository;
	
	@Transactional
	public List<EpisodeListDto> getEpisodeList(int idx) {
		
		Optional<Webtoon> WebtoonEntityWrapper = webtoonRepository.findById(idx);
	    Webtoon webtoon = WebtoonEntityWrapper.get();
	    List<EpisodeListDto> episodeDtoList = new ArrayList<>();
	    WebtoonDto webtoonDto = WebtoonDto.builder()
	    		.episodes(webtoon.getEpisodes())
	    		.build();
	    List<Episode> episodeList = webtoonDto.getEpisodes();
	    for(Episode episode : episodeList) {
	    	EpisodeListDto episodeDto = EpisodeListDto.builder()
	    			.ep_no(episode.getEp_no())
	    			.title(episode.getTitle())
	    			.thumbnail(episode.getThumbnail())
	    			.created_date(episode.getCreated_date())
	    			.build();
	    	episodeDtoList.add(episodeDto);
	    }
	    return episodeDtoList;
        
        /*
		List<Episode> episodeEntities = episodeRepository.findAll();
		List<EpisodeDto> episodeDtoList = new ArrayList<>();
		for(Episode episodeEntity : episodeEntities ) {
			EpisodeDto episdoeDto = EpisodeDto.builder()
					.title(episodeEntity.getTitle())
					.author_comment(episodeEntity.getAuthor_comment())
					.contents(episodeEntity.getContents())
					.ep_no(episodeEntity.getEp_no())
					.build();
			
			episodeDtoList.add(episdoeDto);
		}
		*/
        
		//return episodeDtoList;
	}
	@Transactional
	public int addEpisode(int webtoon_idx, MultipartFile thumbnail, MultipartFile[] manuscripts, EpisodeDto episodeDto) {
		
		//webtoon idx로 해당 webtoon entity 찾고 설정 
		Optional<Webtoon> WebtoonEntityWrapper = webtoonRepository.findById(webtoon_idx);
        Webtoon webtoon = WebtoonEntityWrapper.get();
        //episodeDto에 해당 webtoon 연결
        //episodeDto.setWebtoon(webtoon);
        //episodeDto.toEntity().setWebtoon(webtoon);
       
        /*
         * file관련 db저장 
         */
        
        String thumbnailName = thumbnail.getOriginalFilename();
        episodeDto.setThumbnail(thumbnailName);
        String manuscriptsName = manuscripts[0].getOriginalFilename();
        episodeDto.setContents(manuscriptsName);
        
        
        EpisodeRegistDto ep = new EpisodeRegistDto(episodeDto,webtoon);
      
        //episodeDto 생성 및 저장
		episodeRepository.save(ep.toEntity());
		
		return 0;
	}
	
	public int editEpisode(int idx, MultipartFile thumbnail, MultipartFile[] manuscripts, EpisodeDto episodeDto) {
		Optional<Episode> EpisodeEntityWrapper = episodeRepository.findById(idx);
        Episode episode = EpisodeEntityWrapper.get();
        
        episode.setAuthor_comment(episodeDto.getAuthor_comment());
        episode.setTitle(episodeDto.getTitle());
        if(!thumbnail.isEmpty()) {
        	
        }
        
        /*
        if(!manuscripts[0].isEmpty()) {
        	
        }
        */
        
        episodeRepository.save(episode);
        return 0;
	}


}
