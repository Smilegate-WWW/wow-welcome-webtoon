package com.www.platform.dto;

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
public class CommentsSaveRequestDto {
    private String content;
    private int users_idx;
    private int ep_idx;

    @Builder
    public CommentsSaveRequestDto(String content, int users_idx, int ep_idx) {
        this.content = content;
        this.users_idx = users_idx;
        this.ep_idx = ep_idx;
    }
}
