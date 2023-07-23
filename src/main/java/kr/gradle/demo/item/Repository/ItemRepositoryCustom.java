package kr.gradle.demo.item.Repository;

import kr.gradle.demo.item.dto.ItemSearchDto;
import kr.gradle.demo.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//사용자 정의 인터페이스 작성
public interface ItemRepositoryCustom {

    //    원하는 개수를 가져올수있는 페이징 기능 설계
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
