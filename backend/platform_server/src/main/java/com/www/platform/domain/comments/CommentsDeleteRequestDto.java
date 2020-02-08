package com.www.platform.domain.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Controller���� @RequestBody�� �ܺο��� �����͸� �޴� ��쿣
 * �⺻������ + set�޼ҵ带 ���ؼ��� ���� �Ҵ�˴ϴ�.
 */
@Getter
@Setter
@NoArgsConstructor
public class CommentsDeleteRequestDto {
    private int idx;
    /*
    TODO : useridx
     */

    @Builder
    public CommentsDeleteRequestDto(int idx) {
        this.idx = idx;
    }

    public Comments toEntity() {
        return Comments.builder()
                .idx(idx)
                .build();
    }
}
