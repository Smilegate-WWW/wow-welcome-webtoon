package com.www.platform.service;

import com.www.platform.domain.Response;
import com.www.platform.domain.comments.Comments;
import com.www.platform.domain.comments.CommentsMainResponseDto;
import com.www.platform.domain.comments.CommentsRepository;
import com.www.platform.domain.comments.likedislike.*;
import com.www.platform.domain.fordevtest.Users;
import com.www.platform.domain.fordevtest.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentsLikeDislikeService {
    private UsersRepository usersRepository;
    private CommentsRepository commentsRepository;
    private CommentsLikeRepository commentsLikeRepository;
    private CommentsDislikeRepository commentsDislikeRepository;

    @Transactional
    public Response<CommentsMainResponseDto> requestLike(CommentsLikeRequestDto dto) {
        Response<CommentsMainResponseDto> result = new Response<CommentsMainResponseDto>();
        // TODO : fail : login required
        // TODO : fail : like request user id = comments writer user id

        Optional<Users> users = usersRepository.findById(dto.getUsers_idx());
        Optional<Comments> comments = commentsRepository.findById(dto.getComments_idx());

        CommentsLike commentsLike;

        if(commentsLikeRepository.existsByComments_IdxAndUsers_Idx
                (dto.getComments_idx(), dto.getUsers_idx())){
            // complete : cancel request like

            commentsLike = commentsLikeRepository.findByComments_IdxAndUsers_Idx
                            (dto.getComments_idx(), dto.getUsers_idx());
            commentsLikeRepository.deleteByIdx(commentsLike.getIdx());

            commentsRepository.updateLikeCnt(commentsLike.getComments().getIdx(), -1);
            CommentsMainResponseDto commentsMainResponseDto =
                    new CommentsMainResponseDto(commentsRepository.findById(dto.getComments_idx()).get());
            result.setCode(1);
            result.setMsg("complete : cancel request like");
            result.setData(commentsMainResponseDto);
        }
        else{
            // complete : success request like
            commentsLike = CommentsLike.builder()
                    .users_idx(users.get())
                    .comments_idx(comments.get())
                    .build();
            commentsLikeRepository.save(commentsLike);
            commentsRepository.updateLikeCnt(commentsLike.getComments().getIdx(), 1);

            CommentsMainResponseDto commentsMainResponseDto =
                    new CommentsMainResponseDto(commentsRepository.findById(dto.getComments_idx()).get());

            result.setCode(0);
            result.setMsg("complete : success request like");
            result.setData(commentsMainResponseDto);
        }

        return result;
    }

    /*
    @Transactional
    public Response<CommentsMainResponseDto> requestDislike(CommentsDislikeRequestDto dto) {
        Response<CommentsMainResponseDto> result = new Response<CommentsMainResponseDto>();
        // TODO : fail : login required
        // TODO : fail : dislike request user id = comments writer user id

        Optional<Users> users = usersRepository.findById(dto.getUsers_idx());
        Optional<Comments> comments = commentsRepository.findById(dto.getComments_idx());

        CommentsDislike commentsDislike;

        if(commentsDislikeRepository.existsByComments_IdxAndUsers_Idx
                (dto.getComments_idx(), dto.getUsers_idx())){
            // complete : cancel request like

            commentsDislike = commentsDislikeRepository.findByComments_IdxAndUsers_Idx
                    (dto.getComments_idx(), dto.getUsers_idx());
            commentsDislikeRepository.deleteById(commentsDislike.getIdx());

            commentsRepository.updateDislikeCnt(commentsDislike.getComments().getIdx(), -1);
            CommentsMainResponseDto commentsMainResponseDto =
                    new CommentsMainResponseDto(commentsRepository.findById(dto.getComments_idx()).get());
            result.setCode(1);
            result.setMsg("complete : cancel request dislike");
            result.setData(commentsMainResponseDto);
        }
        else{
            // complete : success request like
            commentsDislike = CommentsDislike.builder()
                    .users_idx(users.get())
                    .comments_idx(comments.get())
                    .build();
            commentsDislikeRepository.save(commentsDislike);
            commentsRepository.updateDislikeCnt(commentsDislike.getComments().getIdx(), 1);

            CommentsMainResponseDto commentsMainResponseDto =
                    new CommentsMainResponseDto(commentsRepository.findById(dto.getComments_idx()).get());

            result.setCode(0);
            result.setMsg("complete : success request dislike");
            result.setData(commentsMainResponseDto);
        }
        return result;
    }
     */
}
