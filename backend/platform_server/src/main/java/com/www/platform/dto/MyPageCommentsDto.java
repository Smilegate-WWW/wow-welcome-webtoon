package com.www.platform.dto;

import com.www.core.platform.entity.Comments;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class MyPageCommentsDto {

    private int idx;
    private String webtoon_thumbnail;
    private String webtoon_title;
    private int ep_no;
    private int like_cnt;
    private int dislike_cnt;
    private String content;

    public MyPageCommentsDto(Comments entity) {
        idx = entity.getIdx();
        webtoon_thumbnail = entity.getEp().getWebtoon().getThumbnail();
        webtoon_title = entity.getEp().getWebtoon().getTitle();
        ep_no = entity.getEp().getEp_no();
        like_cnt = entity.getLike_cnt();
        dislike_cnt = entity.getDislike_cnt();
        content = entity.getContent();
    }
}