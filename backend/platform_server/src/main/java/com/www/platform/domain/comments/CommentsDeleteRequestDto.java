package com.www.platform.domain.comments;

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
public class CommentsDeleteRequestDto {
    private int idx;
    private int users_idx;

    @Builder
    public CommentsDeleteRequestDto(int idx, int users_idx) {
        this.idx = idx;
        this.users_idx = users_idx;
    }
}
