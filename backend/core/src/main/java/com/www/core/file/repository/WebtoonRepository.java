package com.www.core.file.repository;

import com.www.core.file.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface WebtoonRepository extends JpaRepository<Webtoon, Integer>{

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(value = "UPDATE webtoon w " +
            "SET w.ep_rating_avg = (SELECT " +
            "SUM(e.rating_avg * e.rating_person_total) / SUM(e.rating_person_total) " +
            "FROM episode e " +
            "WHERE e.webtoon_idx = :webtoon_idx) " +
            "WHERE w.idx = :webtoon_idx", nativeQuery = true)
    void updateRatingAvg(@Param("webtoon_idx") int webtoonIdx);
}
