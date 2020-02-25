package com.www.platform.controller;

import com.www.core.common.Response;
import com.www.core.common.TokenChecker;
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

    private CommentsService commentsService;
    private CommentsLikeDislikeService commentsLikeDislikeService;
    private TokenChecker tokenChecker;
    //private Environment env;


    @GetMapping("/episodes/{ep-idx}/comments")
    public Response<CommentsResponseDto> getComments(@PathVariable("ep-idx") int epIdx,
                                                           @RequestParam("page") int page) {
        return commentsService.findCommentsByPageRequest(epIdx, page);
    }

    @PostMapping("/episodes/{ep-idx}/comments")
    public Response<Integer> saveComments(@RequestHeader("Authorization") String AccessToken,
                                          @PathVariable("ep-idx")int epIdx,
                                          @RequestBody CommentsSaveRequestDto dto) {
        Response<Integer> result = new Response<Integer>();

        switch(tokenChecker.validateToken(AccessToken)) {
            case 0: // 유효한 토큰
                int usersIdx = tokenChecker.getUserIdx(AccessToken);
                if(-1 == usersIdx){
                    result.setCode(42);
                    result.setMsg("access denied : maybe captured or faked token");
                    break;
                }
                result = commentsService.save(usersIdx, epIdx, dto.getContent());
                break;
            case 1: // 만료된 토큰
                result.setCode(44);
                result.setMsg("access denied : invalid access token");
                break;
            case 2: // 에러,올바르지 않은 토큰
                result.setCode(42);
                result.setMsg("access denied : maybe captured or faked token");
                break;
        }

        return result;
    }

    @DeleteMapping("/episodes/{ep-idx}/comments/{cmt-idx}")
    public Response<Integer> deleteComments(@RequestHeader("Authorization") String AccessToken,
                                            @PathVariable("ep-idx") int epIdx,
                                            @PathVariable("cmt-idx") int commentIdx){
        Response<Integer> result = new Response<Integer>();

        switch(tokenChecker.validateToken(AccessToken)) {
            case 0: // 유효한 토큰
                int usersIdx = tokenChecker.getUserIdx(AccessToken);
                if(-1 == usersIdx){
                    result.setCode(42);
                    result.setMsg("access denied : maybe captured or faked token");
                    break;
                }
                result = commentsService.delete(usersIdx, epIdx, commentIdx);
                break;
            case 1: // 만료된 토큰
                result.setCode(44);
                result.setMsg("access denied : invalid access token");
                break;
            case 2: // 에러,올바르지 않은 토큰
                result.setCode(42);
                result.setMsg("access denied : maybe captured or faked token");
                break;
        }

        return result;
    }

    @GetMapping("/episodes/{ep_idx}/comments/best")
    public Response<List<CommentsDto>> getBestComments(@PathVariable("ep_idx") int epIdx) {
        return commentsService.findBestComments(epIdx);
    }

    @PostMapping("comments/{cmt-idx}/like")
    public Response<CommentsLikeDislikeCntResponseDto> requestCommentsLike(@RequestHeader("Authorization") String AccessToken,
                                                                           @PathVariable("cmt-idx") int commentIdx){
        Response<CommentsLikeDislikeCntResponseDto> result = new Response<CommentsLikeDislikeCntResponseDto>();

        switch(tokenChecker.validateToken(AccessToken)) {
            case 0: // 유효한 토큰
                int usersIdx = tokenChecker.getUserIdx(AccessToken);
                if(-1 == usersIdx){
                    result.setCode(42);
                    result.setMsg("access denied : maybe captured or faked token");
                    break;
                }
                result = commentsLikeDislikeService.requestLike(usersIdx, commentIdx);
                break;
            case 1: // 만료된 토큰
                result.setCode(44);
                result.setMsg("access denied : invalid access token");
                break;
            case 2: // 에러,올바르지 않은 토큰
                result.setCode(42);
                result.setMsg("access denied : maybe captured or faked token");
                break;
        }

        return result;
    }

    @PostMapping("comments/{cmt-idx}/dislike")
    public Response<CommentsLikeDislikeCntResponseDto> requestCommentsDislike(@RequestHeader("Authorization") String AccessToken,
                                                                              @PathVariable("cmt-idx") int commentIdx){
        Response<CommentsLikeDislikeCntResponseDto> result = new Response<CommentsLikeDislikeCntResponseDto>();

        switch(tokenChecker.validateToken(AccessToken)) {
            case 0: // 유효한 토큰
                int usersIdx = tokenChecker.getUserIdx(AccessToken);
                if(-1 == usersIdx){
                    result.setCode(42);
                    result.setMsg("access denied : maybe captured or faked token");
                    break;
                }
                result = commentsLikeDislikeService.requestDislike(usersIdx, commentIdx);
                break;
            case 1: // 만료된 토큰
                result.setCode(44);
                result.setMsg("access denied : invalid access token");
                break;
            case 2: // 에러,올바르지 않은 토큰
                result.setCode(42);
                result.setMsg("access denied : maybe captured or faked token");
                break;
        }

        return result;
    }
}
