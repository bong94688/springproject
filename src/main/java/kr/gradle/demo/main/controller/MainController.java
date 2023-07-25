package kr.gradle.demo.main.controller;

import kr.gradle.demo.item.dto.ItemSearchDto;
import kr.gradle.demo.item.service.ItemService;
import kr.gradle.demo.main.dto.MainItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
//파이널 영역으로 올리면 자동으로 생성자 주입
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping("/")
//    메인페이지에 검색 조건
    public String Main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

//        페이지 어블 객체에 받아온 페이지가 발견이 됐으면 그 페이지를 가져오고 아니면 0부터
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get(): 0 , 6);
//        메인에 띄어줄 아이템은?
        Page<MainItemDto> itemDtos =
                itemService.getMainItemPage(itemSearchDto,pageable);
//        찾아온 페이징 처리된 itemDtos 은 items 라는 거에 매핑해서 보내준다.
        model.addAttribute("items",itemDtos);
        model.addAttribute("itemSearchDto",itemSearchDto);
//        보여줄 한페이지 는 5개씩
        model.addAttribute("maxPage",5);

        return "main";
    }


}
