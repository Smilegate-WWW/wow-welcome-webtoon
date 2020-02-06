package com.www.file.service;

import java.io.File;
import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.www.file.domain.repository.WebtoonRepository;
import com.www.file.dto.WebtoonDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class WebtoonService {
	
	private WebtoonRepository webtoonRepository;
	
	@Transactional
	public void saveWebtoon(WebtoonDto webtoonDto) {
		
		webtoonRepository.save(webtoonDto.toEntity());
	}
	
	@Transactional
	public void createWebtoon(MultipartFile file, WebtoonDto webtoonDto) throws IOException {
		
		
		String fileName = file.getOriginalFilename();
		System.out.println(fileName);
		webtoonDto.setThumbnail(fileName);
		//file 저장소에 저장
		File destinationFile = new File("D:/image/"+fileName);
		destinationFile.getParentFile().mkdir();
		file.transferTo(destinationFile);
		
		//webtoon정보 저장
		webtoonRepository.save(webtoonDto.toEntity());
	
	}

}
