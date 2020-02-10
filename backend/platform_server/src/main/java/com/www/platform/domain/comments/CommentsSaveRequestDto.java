package com.www.platform.domain.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Controller에서 @RequestBody로 외부에서 데이터를 받는 경우엔
 * 기본생성자 + set메소드를 통해서만 값이 할당됩니다.
 */
@Getter
@Setter
@NoArgsConstructor
public class CommentsSaveRequestDto {
    private String content;

    @Builder
    public CommentsSaveRequestDto(String content) {
        this.content = content;
    }

    public Comments toEntity() {
        return Comments.builder()
                .content(content)
                .build();
    }
}
