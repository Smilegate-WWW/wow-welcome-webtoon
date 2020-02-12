package com.www.platform.domain.comments.likedislike;

import com.www.platform.domain.comments.Comments;
import com.www.platform.domain.comments.CommentsRepository;
import com.www.platform.domain.fordevtest.Users;
import com.www.platform.domain.fordevtest.UsersRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

/**
 * Controller���� @RequestBody�� �ܺο��� �����͸� �޴� ��쿣
 * �⺻������ + set�޼ҵ带 ���ؼ��� ���� �Ҵ�˴ϴ�.
 */
@Getter
@Setter
@NoArgsConstructor
public class CommentsLikeRequestDto {
    private int users_idx;
    private int comments_idx;

    public CommentsLikeRequestDto(int users_idx, int comments_idx){
        this.users_idx = users_idx;
        this.comments_idx = comments_idx;
    }
}
