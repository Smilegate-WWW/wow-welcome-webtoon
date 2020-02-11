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
    //private CommentsDislikeRepository commentsDislikeRepository;


    @Transactional
    public Response<CommentsMainResponseDto> requestLike(CommentsLikeRequestDto dto) {
        Response<CommentsMainResponseDto> result = new Response<CommentsMainResponseDto>();
        // TODO : fail : login required
        // TODO : fail : like request user id = comments writer user id

        //if(commentsLikeRepository.existsByComments_IdxAndUsers_Idx
        //        (dto.getComments_idx().getIdx(), dto.getUsers_idx().getIdx())) {
            // complete : cancel request like
        //    commentsLikeRepository.delete(dto.toEntity());
        //     commentsRepository.updateLikeCnt(dto.getComments_idx().getIdx(), -1);
        //}
        //else{
            // complete : success request like
            // 좋아요 테이블 튜플 추가 -> 해당 comments 테이블에 like_cnt 증가 -> 댓글 response dto 반환
            Optional<Users> users = usersRepository.findById(dto.getUsers_idx());
            Optional<Comments> comments = commentsRepository.findById(dto.getComments_idx());

            CommentsLike commentsLike = CommentsLike.builder()
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
        //}

        return result;
    }
}
