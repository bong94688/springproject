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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


//JUnit
//inteliJ -> shift + F9
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
    }

    public void createItemList(){

//        item entity 아이템 채우기
        for(int i=1;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명"+i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100); item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }





    @Test
    @DisplayName("상품명, 상품설명 or 테스트")
    public void findByItemNmOrItemDetailTest(){

        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1","테스트 상품 상세 설명10");

        for(Item item: itemList){
            System.out.println(item.toString());
        }

    }
    
    @Test
    @DisplayName("JPAL 쿼리")
    public void findByItemDetailTest(){
        this.createItemList();
        
        List<Item> itemList = itemRepository.findByItemDetail("테스트");

        for(Item item: itemList){
            System.out.println(item);
        }
    }



    @Test
    @DisplayName("Native 쿼리")
    public void findByItemDetailNativeTest(){
        this.createItemList();

        List<Item> itemList = itemRepository.findByItemDetailNative("테스트");

        for(Item item: itemList){
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("querydsl 테스트2")
    public void querydslTest(){

    }

}