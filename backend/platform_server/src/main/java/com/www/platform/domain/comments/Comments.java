package com.www.platform.domain.comments;

import com.www.platform.domain.BaseCreatedTimeEntity;
import com.www.platform.domain.fordevtest.Episode;
import com.www.platform.domain.fordevtest.Users;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Entity 클래스를 프로젝트 코드상에서 기본생성자로 생성하는 것은 막되, JPA에서 Entity 클래스를 생성하는것은 허용하기 위해 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comments extends BaseCreatedTimeEntity{

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


