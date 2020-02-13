package com.www.platform.domain.comments;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class CommentsMainResponseDto {
    private Integer idx;
    private int ep_idx;
    private int users_idx;
    private int like_cnt;
    private int dislike_cnt;
    private String content;
    private String created_date;

    public CommentsMainResponseDto(Comments entity) {
        idx = entity.getIdx();
        ep_idx = entity.getEp().getIdx();
        users_idx = entity.getUsers().getIdx();
        like_cnt = entity.getLike_cnt();
        dislike_cnt = entity.getDislike_cnt();
        content = entity.getContent();
        created_date = toStringDateTime(entity.getCreated_date());
    }

    private String toStringDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}
