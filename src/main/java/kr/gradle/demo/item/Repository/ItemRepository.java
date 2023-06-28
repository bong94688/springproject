package kr.gradle.demo.item.Repository;

import kr.gradle.demo.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
}
