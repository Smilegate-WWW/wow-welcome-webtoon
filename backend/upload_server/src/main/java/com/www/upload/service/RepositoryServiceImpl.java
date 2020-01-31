package com.www.upload.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.www.upload.domain.entity.WebtoonEntity;
import com.www.upload.domain.repository.WebtoonRepository;


@Service
public class RepositoryServiceImpl implements RepositoryService{
	@Autowired
	private WebtoonRepository webtoonRepository;
	
	public RepositoryServiceImpl() {
		
	}
	@Override
	public WebtoonEntity addWebtoon(WebtoonEntity webtoon) {
		return webtoonRepository.save(webtoon);
	}


}
