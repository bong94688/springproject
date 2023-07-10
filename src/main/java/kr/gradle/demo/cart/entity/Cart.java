package kr.gradle.demo.cart.entity;


import jakarta.persistence.*;
import kr.gradle.demo.member.entity.Member;
import kr.gradle.demo.utils.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long Id;



//  무조건 Lazy-> 지연로딩 !
//    OneToOne -> 패치타잎 -> EAGAR
    @JoinColumn(name = "member_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;



}
