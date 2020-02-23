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
import com.www.platform.dto.CommentsDislikeRequestDto;
import com.www.platform.dto.CommentsLikeDislikeCntResponseDto;
import com.www.platform.dto.CommentsLikeRequestDto;
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

        if(!users.isPresent()){
            result.setCode(2);
            result.setMsg("fail : user not exist");
        }
        else if(!comments.isPresent()){
            result.setCode(3);
            result.setMsg("fail : comment not exist");
        }
        else if(users.get().getIdx() == comments.get().getUsers().getIdx()){
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

        if(!users.isPresent()){
            result.setCode(2);
            result.setMsg("fail : user not exist");
        }
        else if(!comments.isPresent()){
            result.setCode(3);
            result.setMsg("fail : comment not exist");
        }
        else if(users.get().getIdx() == comments.get().getUsers().getIdx()){ 
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
