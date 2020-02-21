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

	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE Episode e " +
			"SET e.rating_avg = (SELECT AVG(rating) " +
			"FROM star_rating s " +
			"WHERE s.ep_idx = :ep_idx) " +
			"WHERE e.idx = :ep_idx", nativeQuery = true)
	void updateRatingAvg(@Param("ep_idx") int epIdx);
}
