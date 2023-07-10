package kr.gradle.demo.item.Repository;

import kr.gradle.demo.item.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemImgRepository extends JpaRepository<ItemImage,Long> {

//   ItemId 을 주면 거기에있는 ItemImgId 를 오름차순으로 꺼내온다.
//    @Query("Select * from ItemImg i Where i.itemId = :itemId") -> jpql

    List<ItemImage> findByItemIdOrderByIdAsc(Long itemId);
}
