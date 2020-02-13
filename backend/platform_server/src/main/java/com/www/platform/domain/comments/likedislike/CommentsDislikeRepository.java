package com.www.platform.domain.comments.likedislike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentsDislikeRepository extends JpaRepository<CommentsDislike, Integer> {
    boolean existsByComments_IdxAndUsers_Idx(int comments_idx, int users_idx);

    CommentsDislike findByComments_IdxAndUsers_Idx(int comments_idx, int users_idx);

    // deleteById�� ������ �ȵǼ� �Ȱ��� ����� ���� ���� ���� ����.
    @Modifying
    @Transactional
    @Query("DELETE FROM comments_dislike " +
            "WHERE idx = :idx")
    void deleteByIdx(@Param("idx") int idx);
}
