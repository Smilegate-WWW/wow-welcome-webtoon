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
    float rating;

    @Builder
    public StarRatingDto(float rating) {
        this.rating = rating;
    }
}
