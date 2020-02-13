package com.www.platform.domain.comments;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Comments> findAll(Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM Comments c " +
                    "WHERE c.like_cnt >= c.dislike_cnt + 5 " +
                    "ORDER BY (c.like_cnt - c.dislike_cnt) DESC, c.like_cnt DESC " +
                    "LIMIT 5")
    Stream<Comments> findBestComments();

    /**
     * clearAutomatically = true�� �����ϸ� ���� ���� �� persistence context clear
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
