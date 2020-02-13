package com.www.platform.domain.fordevtest;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

// 개발 테스트용 임시 entity class

@NoArgsConstructor
@Getter
@Entity
public class Episode{

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idx;

    @Builder
    public Episode(int idx){
        this.idx = idx;
    }
}