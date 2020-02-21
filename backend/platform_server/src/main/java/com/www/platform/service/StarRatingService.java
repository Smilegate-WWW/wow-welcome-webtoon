package com.www.platform.service;

import com.www.core.auth.entity.Users;
import com.www.core.auth.repository.UsersRepository;
import com.www.core.common.Response;
import com.www.core.file.entity.Episode;
import com.www.core.file.repository.EpisodeRepository;
import com.www.core.file.repository.WebtoonRepository;
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
public class StarRatingService {

    private StarRatingRepository starRatingRepository;
    private UsersRepository usersRepository;
    private EpisodeRepository episodeRepository;
    private WebtoonRepository webtoonRepository;

    @Transactional
    public Response<String> insertStarRating(int epIdx, StarRatingDto dto) {
        Response<String> result = new Response<String>();

        if(starRatingRepository.existsByEpIdxAndUsersIdx(epIdx, dto.getUsers_idx()))
        {
            result.setCode(1);
            result.setMsg("fail : have already given rating");
        }
        else
        {
            Optional<Users> user = usersRepository.findById(dto.getUsers_idx());
            Optional<Episode> episode = episodeRepository.findById(epIdx);

            // TODO : user, episode 존재유무 예외처리

            StarRating starRating = StarRating.builder()
                    .users(user.get())
                    .ep(episode.get())
                    .rating(dto.getRating())
                    .build();
            starRatingRepository.save(starRating);

            episodeRepository.updateRatingAvg(epIdx);
            webtoonRepository.updateRatingAvg(episode.get().getWebtoon().getIdx());

            result.setCode(0);
            result.setMsg("complete : insert star rating");
        }

        return result;
    }

    /**
     * public Response<StarRatingDto> getWebtoonRating(int webtoonIdx) {
     *
     *     }
     *
     */
}
