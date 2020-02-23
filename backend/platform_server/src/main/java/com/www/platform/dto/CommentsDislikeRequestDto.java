package com.www.platform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentsDislikeRequestDto {
    private int users_idx;
    private int comments_idx;

    public CommentsDislikeRequestDto(int users_idx, int comments_idx){
        this.users_idx = users_idx;
        this.comments_idx = comments_idx;
    }
}
