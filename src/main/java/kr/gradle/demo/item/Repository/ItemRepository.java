package kr.gradle.demo.item.Repository;

import kr.gradle.demo.item.entity.Item;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item,Long>, QuerydslPredicateExecutor<Item>
// 우리가 만든 구현한 인터페이스를 상속 받고 기능을 추가한다.
    ,ItemRepositoryCustom
{


    List<Item> findByItemNm(String ItemNm);
    List<Item> findByItemNmOrItemDetail(String name,String detail);



//    Item 이라는 entity 테이블에 i 이라는 별칭을주고
    @Query("SELECT i FROM Item i where i.itemDetail like %:ItemDetail% "+
            "order by i.price desc")
    List<Item> findByItemDetail(@Param("ItemDetail") String ItemDetail);

    @Query(value = "SELECT * FROM item  where item_detail like %:ItemDetail% "+
            "order by price asc",nativeQuery = true)
    List<Item> findByItemDetailNative(@Param("ItemDetail") String ItemDetail);

}
