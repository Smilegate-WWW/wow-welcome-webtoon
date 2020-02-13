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

// ���� �׽�Ʈ�� �ӽ� entity class

@NoArgsConstructor
@Getter
@Entity
public class Users{

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idx;

    @Builder
    public Users(int idx){
        this.idx = idx;
    }
}