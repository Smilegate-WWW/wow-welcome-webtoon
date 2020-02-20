package com.www.core.file.repository;

import com.www.core.file.entity.Episode;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EpisodeRepository extends JpaRepository<Episode, Integer>{
	Page<Episode> findAllByWebtoonIdx(Pageable pageable,@Param("webtoon_idx") int webtoon_idx);
	List<Episode> findAllByWebtoonIdx(@Param("webtoon_idx") int webtoon_idx);
}
