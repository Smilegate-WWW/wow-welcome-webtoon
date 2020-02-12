package com.www.platform.domain.comments.likedislike;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsDislikeRepository extends JpaRepository<CommentsDislike, Integer> {
    boolean existsByComments_IdxAndUsers_Idx(int comments_idx, int users_idx);

    CommentsDislike findByComments_IdxAndUsers_Idx(int comments_idx, int users_idx);
}
