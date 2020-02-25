package com.www.core.platform.entity;

import com.www.core.auth.entity.Users;
import com.www.core.file.entity.Episode;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity(name = "star_rating")
public class StarRating {

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
    private float rating;

    @Builder
    public StarRating(Episode ep, Users users, float rating){
        this.ep = ep;
        this.users = users;
        this.rating = rating;
    }
}


