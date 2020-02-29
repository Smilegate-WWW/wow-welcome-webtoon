package com.www.platform.dto;

import com.www.core.platform.entity.Comments;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class CommentsDto {
    private Integer idx;
    private String user_id;
    private int like_cnt;
    private int dislike_cnt;
    private String content;
    private String created_date;

    public CommentsDto(Comments entity) {
        idx = entity.getIdx();
        user_id = entity.getUsers().getUserid();
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