package com.www.core.platform.repository;

import com.www.core.platform.entity.CommentsLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentsLikeRepository extends JpaRepository<CommentsLike, Integer> {
    boolean existsByComments_IdxAndUsers_Idx(int comments_idx, int users_idx);

    CommentsLike findByComments_IdxAndUsers_Idx(int comments_idx, int users_idx);

    @Modifying
    @Transactional
    @Query("DELETE FROM comments_like " +
            "WHERE idx = :idx")
    void deleteByIdx(@Param("idx") int idx);

    @Modifying
    @Transactional
    void deleteAllByCommentsIdx(@Param("comments_idx") int commentsIdx);
}