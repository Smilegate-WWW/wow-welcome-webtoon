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
    private int users_idx;

    @Builder
    public CommentsDeleteRequestDto(int idx, int users_idx) {
        this.idx = idx;
        this.users_idx = users_idx;
    }
}
