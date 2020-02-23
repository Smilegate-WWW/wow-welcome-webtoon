package com.www.core.platform.entity;

import com.www.core.auth.entity.Users;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "comments_like")
public class CommentsLike {

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