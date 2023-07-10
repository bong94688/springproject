package kr.gradle.demo.cart.entity;

import jakarta.persistence.*;
import kr.gradle.demo.item.entity.Item;
import kr.gradle.demo.utils.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long Id;

//   하나의 카트에 여러개의 아이템이 담길수 있다.(cartitem)-> 카트에 담긴 아이템들
    @JoinColumn(name = "cart_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;


//  하나의 아이템이 여러개카트에 담길수 있다.
    @JoinColumn(name = "item_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private int count;

}
