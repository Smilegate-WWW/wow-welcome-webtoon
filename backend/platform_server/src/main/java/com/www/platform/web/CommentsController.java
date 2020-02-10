package com.www.platform.web;

import com.www.platform.domain.Response;
import com.www.platform.domain.comments.CommentsDeleteRequestDto;
import com.www.platform.domain.comments.CommentsMainResponseDto;
import com.www.platform.domain.comments.CommentsSaveRequestDto;
import com.www.platform.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommentsController {

    private CommentsService commentsService;
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
