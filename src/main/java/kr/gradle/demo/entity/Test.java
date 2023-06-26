package kr.gradle.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "test")
public class Test {
    @Id
//  자동으로 증가 시키는것(시퀀스 mysql)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false,length = 20)
//  name => 값을 원하는 값으로 줄수있다.
    private String name;

//   Default 값 주기
    @Column(columnDefinition = "5")
    private int age;

    private String myinfo;
}
