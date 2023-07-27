package kr.gradle.demo.order.service;


import jakarta.persistence.EntityNotFoundException;
import kr.gradle.demo.item.Repository.ItemRepository;
import kr.gradle.demo.item.entity.Item;
import kr.gradle.demo.member.entity.Member;
import kr.gradle.demo.member.repository.MemberRepository;
import kr.gradle.demo.order.dto.OrderDto;
import kr.gradle.demo.order.entity.Order;
import kr.gradle.demo.order.entity.OrderItem;
import kr.gradle.demo.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
//Transactional => jpa 변경감지로 업데이트할수있다.
@Transactional
@RequiredArgsConstructor
public class OrderService {

private final ItemRepository itemRepository;
private final MemberRepository memberRepository;
private final OrderRepository orderRepository;


    public  Long Order(OrderDto orderDto, String email){
//       item 을 가져온 OrderDto 에서 아이디를 찾아서 아이디를 반환
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);

//       member은 email로 member을 찾는다. => createOrder 메소스 실행시키기 위해
        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
//        item을 이용해 OrderItem을 사용한다.
//        만든 orderItem 값 반환하고 List에 채운다.
        OrderItem orderItem = OrderItem.createOrderItem(item,orderDto.getCount());
        orderItemList.add(orderItem);
//       위에서 찾은 order을 받고 오더를 저장한다.
        Order order = Order.createOrder(member,orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
}
