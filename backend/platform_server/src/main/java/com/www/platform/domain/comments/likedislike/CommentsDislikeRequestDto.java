package com.www.platform.domain.comments.likedislike;

import com.www.platform.domain.comments.Comments;
import com.www.platform.domain.fordevtest.Users;
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
public class CommentsDislikeRequestDto {
    private int users_idx;
    private int comments_idx;

    public CommentsDislikeRequestDto(int users_idx, int comments_idx){
        this.users_idx = users_idx;
        this.comments_idx = comments_idx;
    }
}
