package com.www.platform.domain.comments;

import com.www.platform.domain.BaseCreatedTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Entity Ŭ������ ������Ʈ �ڵ�󿡼� �⺻�����ڷ� �����ϴ� ���� ����, JPA���� Entity Ŭ������ �����ϴ°��� ����ϱ� ���� �߰�
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comments extends BaseCreatedTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    /*
    @ManyToOne
    @JoinColumn
    private Episode epIdx;

    @ManyToOne
    @JoinColumn
    private Users usersIdx;
    */

    @Column(nullable = false)
    private int like_cnt;

    @Column(nullable = false)
    private int dislike_cnt;

    @Column(columnDefinition = "TEXT", nullable = false, length = 300)
    private String content;

    @Builder
    public Comments(int like_cnt, int dislike_cnt, String content){
        this.like_cnt = like_cnt;
        this.dislike_cnt = dislike_cnt;
        this.content = content;
    }
}


