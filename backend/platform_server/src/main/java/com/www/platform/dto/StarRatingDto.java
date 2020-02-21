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
public class StarRatingDto {
    // 테스트용 token 사용하게 되면 float rating만 남겨놓음
    int users_idx;
    float rating;

    @Builder
    public StarRatingDto(int users_idx, float rating) {
        this.users_idx = users_idx;
        this.rating = rating;
    }
}
