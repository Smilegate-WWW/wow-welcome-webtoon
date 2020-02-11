package com.www.platform.domain.comments.likedislike;

import com.www.platform.domain.comments.Comments;
import com.www.platform.domain.fordevtest.Users;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class CommentsLikeDto {
    private Integer idx;
    private Users users_idx;
    private Comments comments_idx;

    public CommentsLikeDto(CommentsLike entity) {
        idx = entity.getIdx();
        users_idx = entity.getUsers();
        comments_idx = entity.getComments();
    }
}
