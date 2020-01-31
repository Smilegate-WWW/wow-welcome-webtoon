package com.www.platform.controller;

import com.www.platform.domain.comments.Comments;
import com.www.platform.domain.comments.CommentsSaveRequestDto;
import com.www.platform.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@AllArgsConstructor
public class CommentsController {

    private CommentsService commentsService;
    //private Environment env;

    @GetMapping("/hello")
    public String hello() {
        return "HelloWorld";
    }

    @PostMapping("/comments")
    public Integer saveComments(@RequestBody CommentsSaveRequestDto dto) {
        return commentsService.save(dto);
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
