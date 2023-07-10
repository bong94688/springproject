package kr.gradle.demo.utils.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

//@EntityListeners(value = {AuditingEntityListener}) => AuditingEntityListener.class
//
//MappedSuperclass
//
@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
//  만든날짜 수정가능하지 x
    private LocalDateTime regTime;

    @LastModifiedDate
//   마지막 수정날짜
    private LocalDateTime updateTime;
}
