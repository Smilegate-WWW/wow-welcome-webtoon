package com.www.file.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.www.file.domain.entity.Webtoon;
import com.www.file.domain.repository.EpisodeRepository;
import com.www.file.domain.repository.WebtoonRepository;
import com.www.file.dto.EpisodeDto;
import com.www.file.dto.WebtoonDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WebtoonService {
	
	private WebtoonRepository webtoonRepository;
	private EpisodeRepository episodeRepository;
	
	@Transactional
	public int createWebtoon(MultipartFile file, WebtoonDto webtoonDto) throws IOException {
		
		String fileName = file.getOriginalFilename();
		System.out.println(fileName);
		webtoonDto.setThumbnail(fileName);
		//file 저장소에 저장
		File destinationFile = new File("D:/image/"+fileName);
		destinationFile.getParentFile().mkdir();
		file.transferTo(destinationFile);
		
		//webtoon정보 저장
		webtoonRepository.save(webtoonDto.toEntity());
		return 0;
	
	}
	
	public int addEpisode(int idx, MultipartFile thumbnail, MultipartFile[] manuscripts, EpisodeDto episodeDto) {
		
		//webtoon idx로 해당 webtoon entity 찾고 설정 
		Optional<Webtoon> WebtoonEntityWrapper = webtoonRepository.findById(idx);
        Webtoon webtoon = WebtoonEntityWrapper.get();
        //episodeDto에 해당 webtoon 연결
        episodeDto.setWebtoon(webtoon);
        /*
         * file관련 db저장 
         */
        
        String thumbnailName = thumbnail.getOriginalFilename();
        episodeDto.setThumnail(thumbnailName);
        
        String manuscriptsName = manuscripts[0].getOriginalFilename();
        episodeDto.setContents(manuscriptsName);
        
        //episodeDto 생성 및 저장
		episodeRepository.save(episodeDto.toEntity());
		return 0;
	}

}
