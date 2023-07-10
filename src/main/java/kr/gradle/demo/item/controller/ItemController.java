package kr.gradle.demo.item.controller;


import kr.gradle.demo.item.dto.ItemFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {


    @GetMapping("/admin/item/new")
    public String itemForm(Model model){

        model.addAttribute("ItemFormDto", new ItemFormDto());
        return "item/itemForm";
    }

//    날릴때 데이터의 무결성을 보장하는것이 중요하다. -> Join걸어서 엉뚱한 데이터 가 들어오는것을 막아야된다.


}
