package com.www.platform.controller;

import com.www.core.common.Response;
import com.www.platform.dto.*;
import com.www.platform.service.EpisodeService;
import com.www.platform.service.StarRatingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class EpisodeController {

    EpisodeService episodeService;
    StarRatingService starRatingService;

    //TODO : return type EpisodeRatingAvgResponse로 교체
    @GetMapping("/episodes/{ep_idx}/rating")
    public Response<StarRatingDto> getRatingAvg(@PathVariable("ep_idx") int epIdx) {
        return episodeService.getEpisodeRating(epIdx);
    }

    @PostMapping("/episodes/{ep_idx}/rating")
    public Response<String> insertStarRating(@PathVariable("ep_idx") int epIdx, @RequestBody StarRatingDto dto) {
        return starRatingService.insertStarRating(epIdx, dto);
    }
}
