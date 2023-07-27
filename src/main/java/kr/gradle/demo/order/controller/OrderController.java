package kr.gradle.demo.order.controller;


import jakarta.validation.Valid;
import kr.gradle.demo.order.dto.OrderDto;
import kr.gradle.demo.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public @ResponseBody ResponseEntity order
            (@RequestBody @Valid OrderDto orderDto,
             BindingResult bindingResult, Principal principal)
//   @RequestBody와 @ReponseBody 어노테이션 사용
//   @RequesyBody HTTP 요청의 본문 body에 담긴 내용을 자바 객체로 전달
//   @ResponseBody: 자바 객체를 HTTP요청의 body로 전달
    {
//       Dto 선에 바인딩 결과에 에러가있을경우
//
        if(bindingResult.hasErrors()){
        StringBuilder stringBuilder = new StringBuilder();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        for(FieldError fieldError: fieldErrorList){
            stringBuilder.append(fieldError);
        }
        return new ResponseEntity<String>(stringBuilder.toString(),HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();
        Long orderId;

        try{
            orderId = orderService.Order(orderDto,email);
        }catch (Exception e){

        }
    }
}
