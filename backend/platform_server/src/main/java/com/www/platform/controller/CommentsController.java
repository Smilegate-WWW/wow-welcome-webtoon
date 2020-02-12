package com.www.platform.controller;

import com.www.platform.domain.Response;
import com.www.platform.domain.comments.*;
import com.www.platform.domain.comments.likedislike.CommentsDislike;
import com.www.platform.domain.comments.likedislike.CommentsDislikeRequestDto;
import com.www.platform.domain.comments.likedislike.CommentsLike;
import com.www.platform.domain.comments.likedislike.CommentsLikeRequestDto;
import com.www.platform.domain.fordevtest.Users;
import com.www.platform.domain.fordevtest.UsersRepository;
import com.www.platform.service.CommentsLikeDislikeService;
import com.www.platform.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class CommentsController {

    private UsersRepository usersRepository;
    private CommentsRepository commentsRepository;
    private CommentsService commentsService;
    private CommentsLikeDislikeService commentsLikeDislikeService;
    //private Environment env;

    @GetMapping("/comments")
    public Response<List<CommentsMainResponseDto>> getComments() {
        return commentsService.findAllDesc();
    }

    @PostMapping("/comments")
    public Response<Integer> saveComments(@RequestBody CommentsSaveRequestDto dto) {
        return commentsService.save(dto);
    }

    @DeleteMapping("/comments")
    public Response<Integer> deleteComments(@RequestBody CommentsDeleteRequestDto dto){
        return commentsService.delete(dto);
    }

    @PostMapping("/comments/like")
    public Response<CommentsMainResponseDto> requestCommentsLike(@RequestBody CommentsLikeRequestDto dto) {
        return commentsLikeDislikeService.requestLike(dto);
    }

    /*
    @PostMapping("/comments/dislike")
    public Response<CommentsMainResponseDto> requestCommentsDislike(@RequestBody CommentsDislikeRequestDto dto) {
        return commentsLikeDislikeService.requestDislike(dto);
    }

     */

    /*
    public Response<CommentsMainResponseDto> requestCommentsLike(@RequestBody CommentsLikeRequestDto dto) {
        return commentsLikeDislikeService.requestLike(dto);
    }
     */

    /*
    @PostMapping("/comments/dislike")
    public Response<CommentsMainResponseDto> requestCommentsDisLike(@RequestBody CommentsDislikeRequestDto dto) {

        return new Response<CommentsMainResponseDto>();
    }
    */

    // TODO : Best Comments;
    @GetMapping("/comments/best")
    public Response<List<CommentsMainResponseDto>> getBestComments() {
        return commentsService.findAllDesc();
    }



    /*
    @GetMapping("/profile")
    public String getProfile() {
        return Arrays.stream(env.getActiveProfiles())
                .findFirst()
                .orElse("");
    }
    */
}
