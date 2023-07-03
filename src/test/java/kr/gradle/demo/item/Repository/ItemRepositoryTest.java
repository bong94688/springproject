package kr.gradle.demo.item.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.gradle.demo.item.constant.ItemSellStatus;
import kr.gradle.demo.item.entity.Item;
import kr.gradle.demo.item.entity.QItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static kr.gradle.demo.item.entity.QItem.item;


//JUnit
//inteliJ -> shift + F9
@SpringBootTest
class ItemRepositoryTest {


    @Autowired
    ItemRepository itemRepository;


//   EntityManager 생성 -> Entity JPA에서 사용 x -> Test에서는 안만들어져 있어서 만듬

//    영속성 테스트
//   PersistenceContext -> @Autowired new 안들어도 객체를 메모리에 로딩
    @Autowired
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
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
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
    @DisplayName("Querydsl 테스트")
    public void queryDslTest(){
        this.createItemList();
//       쿼리를 날리기위한 JPAQueryFactory 객체 생성
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

//        QItem qItem = QItem.item; //==   QItem qItem = new QItem("i");

//       querydsl은 fetch 붙여줘야한다.
//       QItem.list => import 시켜주기
        List<Item> list = queryFactory.selectFrom(item) // => select item from => select from
                .where(item.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(item.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(item.price.desc())
                .fetch();

//        JPAQuery<Item> itemJPAQuery = queryFactory.selectFrom(qItem)
//                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
//                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
//                .orderBy(qItem.price.desc());
//
//
//        List<Item> list = itemJPAQuery.fetch();

        for(Item item : list){
            System.out.println(item.toString());
        }

    }
    @Test
    @DisplayName("Querydsl 테스트2")
    public void queryDslTest2(){
        this.createItemList();
//       쿼리를 날리기위한 JPAQueryFactory 객체 생성
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
//       문자열로 이름을 주는것 QItem을 여러개만들거면 vaiable 몇칭은 다르게.
        QItem qItem = new QItem("i");
//       selectFrom(qitem) select + From qitem
        JPAQuery<Item> query  = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "테스트 상품 상세 설명" + "%"))
                .orderBy(qItem.price.desc());
        List<Item> itemList = query.fetch();
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    public void createItemList2(){
        for(int i=1;i<=5;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }

        for(int i=6;i<=10;i++){
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(0);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemRepository.save(item);
        }
    }
    @Test
    @DisplayName("Querydsl 테스트3")
    public void queryDslTest3() {
        createItemList2();
        String itemDetail = "테스트";
        int price = 10003;
        String itemSellState = "SOLD_OUT";

        QItem item = QItem.item;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(item.itemDetail.like("%"+itemDetail+"%"))
                .and(item.price.gt(price));

        if (StringUtils.equals(itemSellState,ItemSellStatus.SELL)){
//           현재 뷰단에서 받아온 itemSellState 가 열거형의 ItemSellStatus.SELL 과 을때


//           DB에서 ItemSellStaus 형태에서 SELL과 같은 형태인 것들만 가져온다.
            builder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));


        }

//       data 도메인에 Pageable형태의
//        PageRequest의 of라는 메소드에 시작페이지(0번 페이지 + 1번 페이지}와 한페이지의 몇개의 페이지로 되어있는지 를 설정
//
        Pageable pageable = PageRequest.of(0,5);

//       findall에 builder라는 곳의 where조건과
//       findall에 pageable 을 넣어서 페이징 구현 + sort도 넣을수있어서 desc asc 구현가능!
        Page<Item> findAll = itemRepository.findAll(builder, pageable);

        System.out.println("전체개수 : " + findAll.getTotalElements());

        List<Item> content = findAll.getContent();

        for (Item item1 : content){
            System.out.println(item1);
        }
    }




}