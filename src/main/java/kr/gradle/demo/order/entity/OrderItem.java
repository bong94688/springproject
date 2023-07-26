package kr.gradle.demo.order.entity;


import jakarta.persistence.*;
import kr.gradle.demo.item.entity.Item;
import kr.gradle.demo.utils.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    //   ManyToOne -> 패치타잎 EAGAR
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;


    //   ManyToOne -> 패치타잎 EAGAR
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private int orderprice;

    private int count;

    public void addOrderItem(OrderItem orderItem){
        orderItem.addOrderItem(orderItem);
        orderItem.setOrder(this.order);
    }

    public 

}
