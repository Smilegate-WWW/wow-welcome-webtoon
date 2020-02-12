package com.www.platform.domain.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {
    @Query("SELECT c " +
            "FROM Comments c " +
            "ORDER BY c.idx DESC")
    Stream<Comments> findAllDesc();

    /**
     * clearAutomatically = true로 설정하면 쿼리 진행 후 persistence context
     * @author by.mo
     */

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Comments c " +
            "SET c.like_cnt = c.like_cnt + :add_cnt " +
            "WHERE c.idx = :comments_idx")
    void updateLikeCnt(@Param("comments_idx") int commentsIdx, @Param("add_cnt") int addCnt);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Comments c " +
            "SET c.dislike_cnt = c.dislike_cnt + :add_cnt " +
            "WHERE c.idx = :comments_idx")
    void updateDislikeCnt(@Param("comments_idx") int commentsIdx, @Param("add_cnt") int addCnt);
}
