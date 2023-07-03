package kr.gradle.demo.item.dto;

import jakarta.persistence.*;
import kr.gradle.demo.item.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class ItemDto {


//  Long으로 잡는이유 제너릭 타잎할때 필요
    private Long id;      //상품 코드


    private String itemNm; //상품 명

    private int price;

    private  int stockNumber; //재고 수량


    //  @Lob => 큰 데이터를 저장하는데 사용하는 데이터형
    private String itemDetail; //상품 상세 설명

    // LocalDate(날짜), Time(시간)

    private String itemSellStatus;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;



}
