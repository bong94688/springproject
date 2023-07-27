package kr.gradle.demo.item.entity;

import jakarta.persistence.*;
import kr.gradle.demo.exception.OutOfStockException;
import kr.gradle.demo.item.constant.ItemSellStatus;
import kr.gradle.demo.item.dto.ItemFormDto;
import kr.gradle.demo.utils.entity.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item extends BaseEntity{
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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemSellStatus itemSellStatus;


    public void removeStock(int strockNumber){
        int resStock = this.stockNumber - strockNumber;
        if(resStock<0){
            throw new OutOfStockException("상품 재고가 부족 합니다.(현재 재고 수량: "+this.stockNumber+")");
        }
        this.stockNumber = resStock;
    }
//   JPA 변경감지 기능을 이용하여 업데이트하면 자동으로 디비에 반영될수 있게 만든다.
    public void updateItem(ItemFormDto itemFormDto){
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }

    public void addStock(int stockNumber){
        this.stockNumber += stockNumber;
    }

//    테 해줘....
//    짐 좀 내려놔




}
