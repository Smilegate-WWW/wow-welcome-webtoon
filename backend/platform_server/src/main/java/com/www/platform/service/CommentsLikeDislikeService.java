package com.www.platform.service;

import com.www.core.auth.entity.Users;
import com.www.core.auth.repository.UsersRepository;
import com.www.core.common.Response;
import com.www.core.platform.entity.Comments;
import com.www.core.platform.entity.CommentsDislike;
import com.www.core.platform.entity.CommentsLike;
import com.www.core.platform.repository.CommentsDislikeRepository;
import com.www.core.platform.repository.CommentsLikeRepository;
import com.www.core.platform.repository.CommentsRepository;
import com.www.platform.dto.CommentsLikeDislikeCntResponseDto;
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
    public Response<CommentsLikeDislikeCntResponseDto> requestLike(int usersIdx, int commentsIdx) {
        Response<CommentsLikeDislikeCntResponseDto> result = new Response<CommentsLikeDislikeCntResponseDto>();

        Optional<Users> users = usersRepository.findById(usersIdx);
        Optional<Comments> comments = commentsRepository.findById(commentsIdx);

        if(!comments.isPresent()){
            result.setCode(21);
            result.setMsg("fail : comment don't exists");
        }
        else if(users.get().getIdx() == comments.get().getUsers().getIdx()){
            result.setCode(24);
            result.setMsg("fail : users can't request like on their own comments");
        }
        else{
            CommentsLike commentsLike;

            if(commentsLikeRepository.existsByComments_IdxAndUsers_Idx
                    (commentsIdx, usersIdx)){
                // complete : cancel request like

                commentsLike = commentsLikeRepository.findByComments_IdxAndUsers_Idx
                        (commentsIdx, usersIdx);
                commentsLikeRepository.deleteByIdx(commentsLike.getIdx());
                commentsRepository.updateLikeCnt(comments.get().getIdx(), -1);

                CommentsLikeDislikeCntResponseDto commentsLikeDislikeCntResponseDto =
                        new CommentsLikeDislikeCntResponseDto(comments.get().getLike_cnt() - 1);
                result.setCode(0);
                result.setMsg("request complete : cancel request like");
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
                result.setMsg("request complete : success request like");
                result.setData(commentsLikeDislikeCntResponseDto);
            }
        }
        return result;
    }


    @Transactional
    public Response<CommentsLikeDislikeCntResponseDto> requestDislike(int usersIdx, int commentsIdx) {
        Response<CommentsLikeDislikeCntResponseDto> result = new Response<CommentsLikeDislikeCntResponseDto>();

        Optional<Users> users = usersRepository.findById(usersIdx);
        Optional<Comments> comments = commentsRepository.findById(commentsIdx);

        if(!comments.isPresent()){
            result.setCode(21);
            result.setMsg("fail : comment don't exists");
        }
        else if(users.get().getIdx() == comments.get().getUsers().getIdx()){ 
            result.setCode(25);
            result.setMsg("fail : users can't request dislike on their own comments");
        }
        else{
            CommentsDislike commentsDislike;

            if(commentsDislikeRepository.existsByComments_IdxAndUsers_Idx
                    (commentsIdx, usersIdx)){
                // complete : cancel request like

                commentsDislike = commentsDislikeRepository.findByComments_IdxAndUsers_Idx
                        (commentsIdx, usersIdx);
                commentsDislikeRepository.deleteByIdx(commentsDislike.getIdx());
                commentsRepository.updateDislikeCnt(comments.get().getIdx(), -1);

                CommentsLikeDislikeCntResponseDto commentsLikeDislikeCntResponseDto =
                        new CommentsLikeDislikeCntResponseDto(comments.get().getDislike_cnt() -1);
                result.setCode(0);
                result.setMsg("request complete : cancel request dislike");
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
                result.setMsg("request complete : success request dislike");
                result.setData(commentsLikeDislikeCntResponseDto);
            }
        }
        return result;
    }
}
