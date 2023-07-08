package kr.gradle.demo.cart.repository;

import kr.gradle.demo.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart,Long> {
}
