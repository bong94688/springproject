package kr.gradle.demo.item.dto;

import kr.gradle.demo.item.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemSearchDto {

    private String searchDateType;

    private ItemSellStatus searchSellStatus;

//  사람어떤방법
    private String searchBy;

// 들고온 검색어
    private String searchQuery = "";

}