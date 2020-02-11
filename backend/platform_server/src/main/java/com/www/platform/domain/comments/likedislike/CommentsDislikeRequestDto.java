package com.www.platform.domain.comments.likedislike;

import com.www.platform.domain.comments.Comments;
import com.www.platform.domain.fordevtest.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Controller에서 @RequestBody로 외부에서 데이터를 받는 경우엔
 * 기본생성자 + set메소드를 통해서만 값이 할당됩니다.
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
