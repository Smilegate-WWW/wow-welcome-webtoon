package com.www.platform.domain.comments.likedislike;

import com.www.platform.domain.BaseCreatedTimeEntity;
import com.www.platform.domain.comments.Comments;
import com.www.platform.domain.fordevtest.Users;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Entity Ŭ������ ������Ʈ �ڵ�󿡼� �⺻�����ڷ� �����ϴ� ���� ����, JPA���� Entity Ŭ������ �����ϴ°��� ����ϱ� ���� �߰�
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "comments_like")
public class CommentsLike {

    // users_idx, comments_idx ��� ����Ű�� �⺻Ű�� �ص� �� ��?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @ManyToOne
    @JoinColumn
    private Users users;

    @ManyToOne
    @JoinColumn
    private Comments comments;

    @Builder
    public CommentsLike(Users users_idx, Comments comments_idx){
        this.users = users_idx;
        this.comments = comments_idx;
    }
}