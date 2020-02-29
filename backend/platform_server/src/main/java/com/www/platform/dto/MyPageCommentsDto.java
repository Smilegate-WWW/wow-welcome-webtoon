package com.www.platform.dto;

import com.www.core.platform.entity.Comments;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class MyPageCommentsDto {

    private String webtoon_thumbnail;
    private String webtoon_title;
    private int ep_no;
    private String content;

    @Builder
    public MyPageCommentsDto(String webtoon_thumbnail, String webtoon_title,
                             int ep_no, String content) {
        this.webtoon_thumbnail = webtoon_thumbnail;
        this.webtoon_title = webtoon_title;
        this.ep_no = ep_no;
        this.content = content;

    }
}