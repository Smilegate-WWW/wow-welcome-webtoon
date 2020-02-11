package com.www.platform.domain.comments.likedislike;

import com.www.platform.domain.BaseCreatedTimeEntity;
import com.www.platform.domain.comments.Comments;
import com.www.platform.domain.fordevtest.Users;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Entity 클래스를 프로젝트 코드상에서 기본생성자로 생성하는 것은 막되, JPA에서 Entity 클래스를 생성하는것은 허용하기 위해 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "comments_like")
public class CommentsLike {

    // users_idx, comments_idx 묶어서 복합키를 기본키로 해도 될 듯?

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