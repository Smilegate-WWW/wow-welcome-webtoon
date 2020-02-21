package com.www.platform.dto;

import lombok.Getter;

@Getter
public class EpisodeRatingAvgResponseDto {
    private float ratingAvg;
    private boolean canSubmitStarRating;

    public EpisodeRatingAvgResponseDto(float ratingAvg, boolean canSubmitStarRating) {
        this.ratingAvg = ratingAvg;
        this.canSubmitStarRating = canSubmitStarRating;
    }
}
