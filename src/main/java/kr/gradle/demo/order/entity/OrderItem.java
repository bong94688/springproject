package kr.gradle.demo.order.entity;


import jakarta.persistence.*;
import kr.gradle.demo.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private int orderprice;

    private int count;

    private LocalDateTime localDateTime;

    private LocalDateTime regTime;
}
