package com.www.platform.web;

import com.www.platform.domain.comments.CommentsMainResponseDto;
import com.www.platform.service.CommentsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class WebController {

    private CommentsService commentsService;


    // 댓글 화면 보기 테스트용
    @GetMapping("/")
    public String main(Model model) {

        /*
        List<CommentsMainResponseDto> dtos = commentsService.findAllDesc();
        System.out.println(dtos.size());
        for (int i = 0; i < dtos.size(); i++) {
            System.out.println(dtos.get(i).getIdx() + dtos.get(i).getContent());
        }
        model.addAttribute("comments",dtos);
        */

        model.addAttribute("comments", commentsService.findAllDesc());
        return "main";
    }

    /*
    @GetMapping("/")
    public String main() {
        return "main";
    }*/
}
