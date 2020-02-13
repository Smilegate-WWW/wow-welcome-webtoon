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
	
	@Transactional
	public int editWebtoon(int idx, MultipartFile file, WebtoonDto webtoonDto) throws IOException {
		Optional<Webtoon> WebtoonEntityWrapper = webtoonRepository.findById(idx);
        Webtoon webtoon = WebtoonEntityWrapper.get();
        
        webtoon.setEnd_flag(webtoonDto.getEnd_flag());
        webtoon.setGenre1(webtoonDto.getGenre1());
        webtoon.setGenre2(webtoonDto.getGenre2());
        webtoon.setPlot(webtoonDto.getPlot());
        webtoon.setSummary(webtoonDto.getSummary());
        webtoon.setTitle(webtoonDto.getTitle());
        webtoon.setToon_type(webtoonDto.getToon_type());
        
        //썸네일 이미지가 변경되었을 경우 
        if(!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			System.out.println(fileName);
			webtoonDto.setThumbnail(fileName);
			//file 저장소에 저장
			File destinationFile = new File("D:/image/"+fileName);
			destinationFile.getParentFile().mkdir();
			file.transferTo(destinationFile);
		}
        
        webtoonRepository.save(webtoon);
        return 0;
	}

}
