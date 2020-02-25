package com.www.platform.dto;

import com.www.core.platform.entity.Comments;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Getter
public class CommentsResponseDto {
    private List<CommentsDto> comments;
    private int total_page;

    @Builder
    public CommentsResponseDto(List<CommentsDto> comments, int total_page) {
        this.comments = comments;
        this.total_page = total_page;
    }
}
