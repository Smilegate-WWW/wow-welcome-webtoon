package com.www.platform.service;

import com.www.core.auth.entity.Users;
import com.www.core.auth.repository.UsersRepository;
import com.www.core.common.Response;
import com.www.core.file.entity.Episode;
import com.www.core.file.repository.EpisodeRepository;
import com.www.core.file.repository.WebtoonRepository;
import com.www.core.platform.entity.StarRating;
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

    public Response<StarRatingRequestDto> getEpisodeRating(int epIdx) {
        Response<StarRatingRequestDto> result = new Response<StarRatingRequestDto>();

        // TODO : episode 존재유무 예외처리

        StarRatingRequestDto starRatingDto =
                new StarRatingRequestDto(starRatingRepository.getRatingAvgByEpIdx(epIdx));
        result.setCode(0);
        result.setMsg("complete : get episode star rating");
        result.setData(starRatingDto);
        return result;
    }

    @Transactional
    public Response<EpisodeStarRatingResponseDto> insertStarRating(int epIdx, int usersIdx, float rating) {
        Response<EpisodeStarRatingResponseDto> result = new Response<EpisodeStarRatingResponseDto>();

        if(starRatingRepository.existsByEpIdxAndUsersIdx(epIdx, usersIdx))
        {
            result.setCode(1);
            result.setMsg("fail : have already given rating");
        }
        else
        {
            Optional<Users> user = usersRepository.findById(usersIdx);
            Optional<Episode> episode = episodeRepository.findById(epIdx);

            // TODO : episode 존재유무 예외처리

            StarRating starRating = StarRating.builder()
                    .users(user.get())
                    .ep(episode.get())
                    .rating(rating)
                    .build();
            starRatingRepository.save(starRating);

            episodeRepository.updateRatingAvgAndPersonTotal(epIdx);
            webtoonRepository.updateRatingAvg(episode.get().getWebtoon().getIdx());

            episode = episodeRepository.findById(epIdx);

            EpisodeStarRatingResponseDto episodeStarRatingResponseDto
                    = EpisodeStarRatingResponseDto.builder()
                    .rating(episode.get().getRating_avg())
                    .person_total(episode.get().getRating_person_total())
                    .build();

            result.setCode(0);
            result.setMsg("complete : insert star rating");
            result.setData(episodeStarRatingResponseDto);
        }

        return result;
    }
}
