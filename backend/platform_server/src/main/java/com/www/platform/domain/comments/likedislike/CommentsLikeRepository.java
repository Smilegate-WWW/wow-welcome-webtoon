package com.www.platform.domain.comments.likedislike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CommentsLikeRepository extends JpaRepository<CommentsLike, Integer> {
    //boolean existsByComments_IdxAndUsers_Idx(int comments_idx, int users_idx);
}