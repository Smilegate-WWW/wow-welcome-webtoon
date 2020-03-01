package com.www.core.file.repository;

import com.www.core.file.entity.Episode;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EpisodeRepository extends JpaRepository<Episode, Integer>{
	Page<Episode> findAllByWebtoonIdx(Pageable pageable,@Param("webtoon_idx") int webtoon_idx);
	List<Episode> findAllByWebtoonIdx(@Param("webtoon_idx") int webtoon_idx);
	
	/*
	@Modifying
	@Transactional
	@Query(value = "select from Episode where webtoon_idx =:webtoon_idx and ep_no =: no ")
	Episode findbyWebtoonIdxAndNo(@Param("webtoon_idx") int webtoon_idx, @Param("ep_no") int no);
	*/
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE Episode e " +
			"SET e.rating_avg = (SELECT AVG(rating) " +
			"FROM star_rating s " +
			"WHERE s.ep_idx = :ep_idx) , e.rating_person_total = e.rating_person_total + 1 " +
			"WHERE e.idx = :ep_idx", nativeQuery = true)
	void updateRatingAvgAndPersonTotal(@Param("ep_idx") int epIdx);
}
