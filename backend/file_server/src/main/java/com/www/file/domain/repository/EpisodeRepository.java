package com.www.file.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.www.file.domain.entity.Episode;

public interface EpisodeRepository extends JpaRepository<Episode, Integer>{
	 Page<Episode> findAllByWebtoonIdx(Pageable pageable,@Param("webtoon_idx") int webtoon_idx);
	 List<Episode> findAllByWebtoonIdx(@Param("webtoon_idx") int webtoon_idx);
	 
}
