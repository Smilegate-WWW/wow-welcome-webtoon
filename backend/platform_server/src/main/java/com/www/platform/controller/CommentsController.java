package com.www.platform.controller;

import com.www.core.auth.repository.UsersRepository;
import com.www.core.common.Response;
import com.www.core.platform.repository.CommentsRepository;
import com.www.platform.dto.*;
import com.www.platform.service.CommentsLikeDislikeService;
import com.www.platform.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommentsController {

    private UsersRepository usersRepository;
    private CommentsRepository commentsRepository;
    private CommentsService commentsService;
    private CommentsLikeDislikeService commentsLikeDislikeService;
    //private Environment env;

    @GetMapping("/comments/{ep_idx}")
    public Response<Page<CommentsMainResponseDto>> getComments(@PathVariable("ep_idx") int epIdx,
                                                               @RequestParam("page") int page) {
        return commentsService.findCommentsByPageRequest(epIdx, page);
    }

    @PostMapping("/comments")
    public Response<Integer> saveComments(@RequestBody CommentsSaveRequestDto dto) {
        return commentsService.save(dto);
    }

    @DeleteMapping("/comments")
    public Response<Integer> deleteComments(@RequestBody CommentsDeleteRequestDto dto){
        return commentsService.delete(dto);
    }

    @GetMapping("/comments/best/{ep_idx}")
    public Response<List<CommentsMainResponseDto>> getBestComments(@PathVariable("ep_idx") int epIdx) {
        return commentsService.findBestComments(epIdx);
    }

    @PostMapping("/comments/like")
    public Response<CommentsLikeDislikeCntResponseDto> requestCommentsLike(@RequestBody CommentsLikeRequestDto dto) {
        return commentsLikeDislikeService.requestLike(dto);
    }

    @PostMapping("/comments/dislike")
    public Response<CommentsLikeDislikeCntResponseDto> requestCommentsDislike(@RequestBody CommentsDislikeRequestDto dto) {
        return commentsLikeDislikeService.requestDislike(dto);
    }

}
