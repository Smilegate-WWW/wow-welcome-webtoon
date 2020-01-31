package com.www.platform.domain.comments;

import com.www.platform.domain.BaseCreatedTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// Entity 클래스를 프로젝트 코드상에서 기본생성자로 생성하는 것은 막고 JPA에서 Entity 클래스를 생성하는것은 허용하기 위해 추가
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comments extends BaseCreatedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    // TODO : db 초기 모델링 확정 짓고 fk(ep_info_dix, users_idx) 추가, 이전에 테스트할 때는 fk빼고 h2 db로 테스트

    // TODO : 테스트용으로 만든거고 fk로 바꿔야 됨. 타입도 바꿔야 됨.
    @Column(columnDefinition = "VARCHAR", length = 20)
    private String userId;

    @Column(nullable = false)
    private int like;

    @Column(nullable = false)
    private int dislike;

    @Column(columnDefinition = "TEXT", nullable = false, length = 500)
    private String content;

    @Builder
    public Comments(String userId, int like, int dislike, String content) {
        this.userId = userId;
        this.like = like;
        this.dislike = dislike;
        this.content = content;
    }
}
