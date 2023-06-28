package kr.gradle.demo.item.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.gradle.demo.item.constant.ItemSellStatus;
import kr.gradle.demo.item.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
class ItemRepositoryTest {


    @Autowired
    ItemRepository itemRepository;


    @PersistenceContext
    EntityManager em;
    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    @Test
    @DisplayName("JPQL쿼리")
    public void findByItemDetailTest(){


    }
}