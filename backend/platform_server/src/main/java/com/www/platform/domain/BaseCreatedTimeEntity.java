package com.www.platform.domain;

import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// TODO : Core 혹은 Common 이라는 이름으로 모듈 하나 더 만들어서 공통으로 쓰는 것들 관리 해야 할 듯???

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseCreatedTimeEntity {

    @LastModifiedDate
    private LocalDateTime createdDate;
}
