package com.www.core.platform.entity;

import com.www.core.auth.entity.Users;
import com.www.core.common.BaseCreatedTimeEntity;
import com.www.core.file.entity.Episode;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comments extends BaseCreatedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Episode ep;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Users users;

    @Column(nullable = false)
    private int like_cnt;

    @Column(nullable = false)
    private int dislike_cnt;

    @Column(columnDefinition = "TEXT", nullable = false, length = 300)
    private String content;

    @Builder
    public Comments(Episode ep, Users users, int idx, String content){
        this.ep = ep;
        this.users = users;
        this.idx = idx;
        this.content = content;
    }
}


