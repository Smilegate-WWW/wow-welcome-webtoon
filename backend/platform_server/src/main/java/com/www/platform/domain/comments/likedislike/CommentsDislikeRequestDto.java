package com.www.platform.domain.comments.likedislike;

import com.www.platform.domain.comments.Comments;
import com.www.platform.domain.fordevtest.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Controller���� @RequestBody�� �ܺο��� �����͸� �޴� ��쿣
 * �⺻������ + set�޼ҵ带 ���ؼ��� ���� �Ҵ�˴ϴ�.
 */
@Getter
@Setter
@NoArgsConstructor
public class CommentsDislikeRequestDto {
    private Users users_idx;
    private Comments comments_idx;

    @Builder
    public CommentsDislikeRequestDto(Users users_idx, Comments comments_idx) {
        this.users_idx = users_idx;
        this.comments_idx = comments_idx;
    }

    public CommentsDislike toEntity() {
        return CommentsDislike.builder()
                .users_idx(users_idx)
                .comments_idx(comments_idx)
                .build();
    }
}
