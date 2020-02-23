package com.www.platform.controller;

import com.www.core.common.Response;
import com.www.core.common.TokenChecker;
import com.www.platform.dto.*;
import com.www.platform.service.StarRatingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class StarRatingController {

    private StarRatingService starRatingService;
    private TokenChecker tokenChecker;

    //TODO : return type EpisodeRatingAvgResponse로 교체, 없어도 될 것 같기도?
    @GetMapping("/episodes/{ep_idx}/rating")
    public Response<StarRatingRequestDto> getRatingAvg(@PathVariable("ep_idx") int epIdx) {
        return starRatingService.getEpisodeRating(epIdx);
    }

    @PostMapping("/episodes/{ep_idx}/rating")
    public Response<EpisodeStarRatingResponseDto> insertStarRating(@RequestHeader("Authorization") String AccessToken,
                                             @PathVariable("ep_idx")int epIdx, @RequestBody StarRatingRequestDto dto) {
        Response<EpisodeStarRatingResponseDto> result = new Response<EpisodeStarRatingResponseDto>();

        switch(tokenChecker.validateToken(AccessToken)) {
            case 0: // 유효한 토큰
                int usersIdx = tokenChecker.getUserIdx(AccessToken);
                if(-1 == usersIdx){
                    result.setCode(42);
                    result.setMsg("access denied : maybe captured or faked token");
                    break;
                }
                result = starRatingService.insertStarRating(epIdx, usersIdx, dto.getRating());
                break;
            case 1: // 만료된 토큰
                result.setCode(44);
                result.setMsg("access denied : invalid access token");
                break;
            case 2: // 에러,올바르지 않은 토큰
                result.setCode(42);
                result.setMsg("access denied : maybe captured or faked token");
                break;
        }

        return result;
    }
}
