package com.www.platform.service;

import com.www.core.auth.entity.Users;
import com.www.core.auth.repository.UsersRepository;
import com.www.core.common.Response;
import com.www.core.file.entity.Episode;
import com.www.core.file.repository.EpisodeRepository;
import com.www.core.platform.entity.Comments;
import com.www.core.platform.entity.CommentsDislike;
import com.www.core.platform.entity.CommentsLike;
import com.www.core.platform.entity.StarRating;
import com.www.core.platform.repository.CommentsDislikeRepository;
import com.www.core.platform.repository.CommentsLikeRepository;
import com.www.core.platform.repository.CommentsRepository;
import com.www.core.platform.repository.StarRatingRepository;
import com.www.platform.dto.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class EpisodeService {

    private StarRatingRepository starRatingRepository;

    public Response<StarRatingDto> getEpisodeRating(int epIdx) {
        Response<StarRatingDto> result = new Response<StarRatingDto>();

        StarRatingDto starRatingDto =
                new StarRatingDto(0, starRatingRepository.getRatingAvgByEpIdx(epIdx));
        result.setCode(0);
        result.setMsg("complete : get average rating");
        result.setData(starRatingDto);
        return result;
    }
}
