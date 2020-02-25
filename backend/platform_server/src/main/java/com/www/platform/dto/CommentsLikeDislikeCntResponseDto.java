package com.www.platform.dto;

import lombok.Getter;

@Getter
public class CommentsLikeDislikeCntResponseDto {
    private int cnt;

    public CommentsLikeDislikeCntResponseDto(int cnt) {
        this.cnt = cnt;
    }
}
