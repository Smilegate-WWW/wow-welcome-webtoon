package com.www.core.platform.repository;

import com.www.core.platform.entity.Comments;
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

    Page<Comments> findAllByEpIdx(Pageable pageable, @Param("ep_idx") int epIdx);

    Page<Comments> findAllByUsersIdx(Pageable pageable, @Param("users_idx") int userIdx);

    @Query(nativeQuery = true,
            value = "SELECT * " +
                    "FROM Comments c " +
                    "WHERE c.like_cnt >= c.dislike_cnt + 5 AND c.ep_idx = :ep_idx " +
                    "ORDER BY (c.like_cnt - c.dislike_cnt) DESC, c.like_cnt DESC " +
                    "LIMIT 5")
    Stream<Comments> findBestCommentsByEpIdx(@Param("ep_idx") int epIdx);

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
