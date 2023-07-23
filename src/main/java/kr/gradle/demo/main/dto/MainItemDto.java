package kr.gradle.demo.main.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {


    private Long id;

//   메인 화면에 아이템 담당에서 쓸 itemNm
    private String itemNm;

//    아이템 디테일
    private String itemDetail;

//    이미지 를 띄울 imgUrl
    private String imgUrl;

//   가격
    private Integer price;

//    @QueryProjection
//    => Querydsl로 결과 조회시 MainItemDto 객체로 바로 받아 사용
//    @QueryProjection를 이용하면 위에서 발생한 불변 객체 선언, 생성자 그대로 사용을 할 수 있어 권장하는 패턴입니다.
    @QueryProjection
    public MainItemDto(Long id, String itemNm, String itemDetail, String imgUrl, Integer price) {
        this.id = id;
        this.itemNm = itemNm;
        this.itemDetail = itemDetail;
        this.imgUrl = imgUrl;
        this.price = price;
    }

//
}
