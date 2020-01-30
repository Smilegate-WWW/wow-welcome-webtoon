package com.www.platform.controller;

import com.www.platform.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class WebController {

    private CommentsService commentsService;

    // 댓글 화면 보기 테스트용
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("comments", commentsService.findAllDesc());
        return "main";
    }
}
