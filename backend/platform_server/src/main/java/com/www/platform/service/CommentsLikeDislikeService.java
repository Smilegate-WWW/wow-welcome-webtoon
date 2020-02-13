package com.www.platform.service;

import com.www.platform.domain.Response;
import com.www.platform.domain.comments.Comments;
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
    public Response<CommentsLikeDislikeCntResponseDto> requestLike(CommentsLikeRequestDto dto) {
        Response<CommentsLikeDislikeCntResponseDto> result = new Response<CommentsLikeDislikeCntResponseDto>();
        // TODO : fail : login required

        Optional<Users> users = usersRepository.findById(dto.getUsers_idx());
        Optional<Comments> comments = commentsRepository.findById(dto.getComments_idx());

        if(!users.isPresent()){ // 유저가 존재하지 않을 때
            result.setCode(2);
            result.setMsg("fail : user not exist");
        }
        else if(!comments.isPresent()){ // 댓글이 존재하지 않을 때
            result.setCode(3);
            result.setMsg("fail : comment not exist");
        }
        else if(users.get().getIdx() == comments.get().getUsers().getIdx()){ // 본인 댓글에 좋아요 요청을 할 때 요청 실패
            result.setCode(4);
            result.setMsg("fail : users can't request like on their own comments");
        }
        else{
            CommentsLike commentsLike;

            if(commentsLikeRepository.existsByComments_IdxAndUsers_Idx
                    (dto.getComments_idx(), dto.getUsers_idx())){
                // complete : cancel request like

                commentsLike = commentsLikeRepository.findByComments_IdxAndUsers_Idx
                        (dto.getComments_idx(), dto.getUsers_idx());
                commentsLikeRepository.deleteByIdx(commentsLike.getIdx());
                commentsRepository.updateLikeCnt(comments.get().getIdx(), -1);

                CommentsLikeDislikeCntResponseDto commentsLikeDislikeCntResponseDto =
                        new CommentsLikeDislikeCntResponseDto(comments.get().getLike_cnt() - 1);
                result.setCode(1);
                result.setMsg("complete : cancel request like");
                result.setData(commentsLikeDislikeCntResponseDto);
            }
            else{
                // complete : success request like
                commentsLike = CommentsLike.builder()
                        .users_idx(users.get())
                        .comments_idx(comments.get())
                        .build();
                commentsLikeRepository.save(commentsLike);
                commentsRepository.updateLikeCnt(comments.get().getIdx(), 1);

                CommentsLikeDislikeCntResponseDto commentsLikeDislikeCntResponseDto =
                        new CommentsLikeDislikeCntResponseDto(comments.get().getLike_cnt() + 1);

                result.setCode(0);
                result.setMsg("complete : success request like");
                result.setData(commentsLikeDislikeCntResponseDto);
            }
        }
        return result;
    }


    @Transactional
    public Response<CommentsLikeDislikeCntResponseDto> requestDislike(CommentsDislikeRequestDto dto) {
        Response<CommentsLikeDislikeCntResponseDto> result = new Response<CommentsLikeDislikeCntResponseDto>();
        // TODO : fail : login required

        Optional<Users> users = usersRepository.findById(dto.getUsers_idx());
        Optional<Comments> comments = commentsRepository.findById(dto.getComments_idx());

        if(!users.isPresent()){ // 유저가 존재하지 않을 때
            result.setCode(2);
            result.setMsg("fail : user not exist");
        }
        else if(!comments.isPresent()){ // 댓글이 존재하지 않을 때
            result.setCode(3);
            result.setMsg("fail : comment not exist");
        }
        else if(users.get().getIdx() == comments.get().getUsers().getIdx()){ // 본인 댓글에 싫어요 요청을 할 때 요청 실패
            result.setCode(4);
            result.setMsg("fail : users can't request dislike on their own comments");
        }
        else{
            CommentsDislike commentsDislike;

            if(commentsDislikeRepository.existsByComments_IdxAndUsers_Idx
                    (dto.getComments_idx(), dto.getUsers_idx())){
                // complete : cancel request like

                commentsDislike = commentsDislikeRepository.findByComments_IdxAndUsers_Idx
                        (dto.getComments_idx(), dto.getUsers_idx());
                commentsDislikeRepository.deleteByIdx(commentsDislike.getIdx());
                commentsRepository.updateDislikeCnt(comments.get().getIdx(), -1);

                CommentsLikeDislikeCntResponseDto commentsLikeDislikeCntResponseDto =
                        new CommentsLikeDislikeCntResponseDto(comments.get().getDislike_cnt() -1);
                result.setCode(1);
                result.setMsg("complete : cancel request dislike");
                result.setData(commentsLikeDislikeCntResponseDto);
            }
            else{
                // complete : success request like
                commentsDislike = CommentsDislike.builder()
                        .users_idx(users.get())
                        .comments_idx(comments.get())
                        .build();
                commentsDislikeRepository.save(commentsDislike);
                commentsRepository.updateDislikeCnt(comments.get().getIdx(), 1);

                CommentsLikeDislikeCntResponseDto commentsLikeDislikeCntResponseDto =
                        new CommentsLikeDislikeCntResponseDto(comments.get().getDislike_cnt() + 1);
                result.setCode(0);
                result.setMsg("complete : success request dislike");
                result.setData(commentsLikeDislikeCntResponseDto);
            }
        }
        return result;
    }
}
