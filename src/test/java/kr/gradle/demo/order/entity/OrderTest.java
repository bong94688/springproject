package kr.gradle.demo.order.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import kr.gradle.demo.item.Repository.ItemRepository;
import kr.gradle.demo.item.constant.ItemSellStatus;
import kr.gradle.demo.item.entity.Item;
import kr.gradle.demo.member.entity.Member;
import kr.gradle.demo.member.repository.MemberRepository;
import kr.gradle.demo.order.repository.OrderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;


    @Autowired
    EntityManager entityManager;


    @Autowired
    MemberRepository memberRepository;
    public Item createItem(){

        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("상세설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());

        return item;
    }


   @Test
   @DisplayName("영속성 전이 테스트")
   public void  cascadeTest(){

       Order order = new Order();


       for(int i = 0; i<10; i++) {
           Item item = createItem();
           itemRepository.save(item);

           OrderItem orderItem = new OrderItem();

           orderItem.setItem(item);

//           orderItem.setId(1L+i);
           orderItem.setCount(10);
           orderItem.setOrderprice(1000);
           orderItem.setRegTime(LocalDateTime.now());
           orderItem.setUpdateTime(LocalDateTime.now());
           orderItem.setOrder(order);
           order.getOrderItems().add(orderItem);
       }

       orderRepository.saveAndFlush(order);
       entityManager.clear();
       Order saveOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
       System.out.println(saveOrder.getOrderItems().size());
       assertEquals(10,saveOrder.getOrderItems().size());

   }
    public Order createOrder(){
        Order order = new Order();
        for(int i=0;i<3;i++){
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderprice(1000);
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }
        Member member = new Member();
        memberRepository.save(member);
        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest(){
        Order order = this.createOrder();
        order.getOrderItems().remove(0);
        entityManager.flush();
    }







}