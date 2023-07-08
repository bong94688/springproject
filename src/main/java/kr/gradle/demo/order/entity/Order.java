package kr.gradle.demo.order.entity;


import jakarta.persistence.*;
import kr.gradle.demo.member.entity.Member;
import kr.gradle.demo.order.constant.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Order_id")
    private Long id;

//   사용자 가 여러개의 주문이 있을수 있다..
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;


//  OneToMany 속성값으로 연관관계의 주인 설정. 하나의 오더에 여러개 아이템을 가져오도록 !
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime localDateTime;

    private OrderStatus orderStatus;

    private LocalDateTime regTime;

    private LocalDateTime updatetime;


}
