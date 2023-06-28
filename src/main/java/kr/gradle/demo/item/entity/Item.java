package kr.gradle.demo.item.entity;

import jakarta.persistence.*;
import kr.gradle.demo.item.constant.ItemSellStatus;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//  컬럼 이름 값
    @Column(name = "item_id")
//  Long으로 잡는이유 제너릭 타잎할때 필요
    private Long id;      //상품 코드


    @Column(length = 50,nullable = false)
    private String itemNm; //상품 명

    private int price;

    @Column(nullable = false,name = "number")
    private  int stockNumber; //재고 수량


//  @Lob => 큰 데이터를 저장하는데 사용하는 데이터형
    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    // LocalDate(날짜), Time(시간)
    private LocalDateTime regTime;

    private LocalDateTime updateTime;

    private ItemSellStatus itemSellStatus;




//    테 해줘....
//    짐 좀 내려놔




}
