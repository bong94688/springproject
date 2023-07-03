package kr.gradle.demo.thymeleaf.controller;

import kr.gradle.demo.item.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class thymeleafcontroller {


    @GetMapping("/ex1")
    public String ex1(Model model){
        Point p = new Point(10,20);
        model.addAttribute("data", p);

        return "thymeleaf/ex1";
    }

    @GetMapping("/ex2")
    public String ex2(Model model){

        ItemDto itemDto = new ItemDto();

        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품 1");
        itemDto.setPrice(3000);
        itemDto.setItemSellStatus("SELL");
        itemDto.setRegTime(LocalDateTime.now());


        model.addAttribute("itemDto",itemDto);
        return "thymeleaf/ex2";
    }

//   void 로 설정하면 Getmapping일때 그대로 /thymeleaf/ex3위치에 html을 찾아서 리턴해준다.
    @GetMapping(value = {"/ex3","/ex4"})
    public void ex3(Model model) {


        List<ItemDto> list = new ArrayList<>();


        for (int i = 1; i <= 10; i++){
            ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명" +i);
        itemDto.setItemNm("테스트 상품"+i);
        itemDto.setPrice(3000);
        itemDto.setItemSellStatus("SELL");
        itemDto.setRegTime(LocalDateTime.now());list.add(itemDto);
        }

        model.addAttribute("list",list);

    }
}
