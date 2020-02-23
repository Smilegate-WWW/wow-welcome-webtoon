package com.www.platform.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentsDeleteRequestDto {
    private int idx;
    private int users_idx;

    @Builder
    public CommentsDeleteRequestDto(int idx, int users_idx) {
        this.idx = idx;
        this.users_idx = users_idx;
    }
}
