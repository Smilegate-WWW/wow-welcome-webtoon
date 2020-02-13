package com.www.platform.domain.comments.likedislike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentsLikeRepository extends JpaRepository<CommentsLike, Integer> {
    boolean existsByComments_IdxAndUsers_Idx(int comments_idx, int users_idx);

    CommentsLike findByComments_IdxAndUsers_Idx(int comments_idx, int users_idx);

    // deleteById가 실행이 안되서 똑같은 기능을 가진 쿼리 따로 만듬.
    @Modifying
    @Transactional
    @Query("DELETE FROM comments_like " +
            "WHERE idx = :idx")
    void deleteByIdx(@Param("idx") int idx);
}