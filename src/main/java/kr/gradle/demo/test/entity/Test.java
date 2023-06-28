package kr.gradle.demo.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "kr/gradle/demo/test")
public class Test {

    @Id
    private Long id;

    private String name;

    private int age;
}
