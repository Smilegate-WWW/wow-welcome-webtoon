package com.www.platform.domain.comments;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class CommentsMainResponseDto {
    private Integer idx;
    private String userId;
    private int like;
    private int dislike;
    private String content;
    private String createdDate;

    public CommentsMainResponseDto(Comments entity) {
        idx = entity.getIdx();
        userId = entity.getUserId();
        like = entity.getLike();
        dislike = entity.getDislike();
        content = entity.getContent();
        createdDate = toStringDateTime(entity.getCreatedDate());
    }

    private String toStringDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}
