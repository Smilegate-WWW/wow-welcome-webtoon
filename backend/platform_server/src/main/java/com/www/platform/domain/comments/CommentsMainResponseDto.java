package com.www.platform.domain.comments;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class CommentsMainResponseDto {
    private Integer idx;
    //private Episode epIdx;
    //private Users usersIdx;
    private int like_cnt;
    private int dislike_cnt;
    private String content;
    private String created_date;

    public CommentsMainResponseDto(Comments entity) {
        idx = entity.getIdx();
        //epIdx = entity.getEpIdx();
        //usersIdx = entity.getUsersIdx();
        like_cnt = entity.getLike_cnt();
        dislike_cnt = entity.getDislike_cnt();
        content = entity.getContent();
        //created_date = LocalDateTime.now();
        created_date = toStringDateTime(entity.getCreated_date());
    }

    private String toStringDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}
