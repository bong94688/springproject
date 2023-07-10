package kr.gradle.demo.item.controller;


import jakarta.validation.Valid;
import kr.gradle.demo.item.dto.ItemFormDto;
import kr.gradle.demo.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;



    @GetMapping("/admin/item/new")
    public String itemForm(Model model){

        model.addAttribute("ItemFormDto", new ItemFormDto());
        return "item/itemForm";
    }





//  아이템
    @PostMapping("/admin/item/new")
//     @NotBlank(message = "상품명은 필수 입력 값입니다.") 이런값들 뜨게만들기위한 @Valid
//    에러메시지 를 뷰에 보낼수있는 Model
//    itemImgFile으로 날라오는 5개의 List를  @RequestParam 으로 받아준다.!
//    itemImgFile은  th:field="*{itemDetail} 이런거와같이 get방식으로 보낼때 생성자 보내줬었는디
//    그걸로 받지않고 name 값으로 받아서 RequestParam 써줘야됨!1
//    수정과 저장 동시에 한 html에서 될수있게 만든다!
    public String itemFormPos(@Valid ItemFormDto itemFormDto, BindingResult bindingResult, Model model,
                              @RequestParam("itemImgFile")List<MultipartFile> multipartFiles) {

//        bindingResult 뷰에서 보내준 bindingResult에 에러가 들어있으면 다시 Form으로 돌려보내준다.
        if(bindingResult.hasErrors()){
//            에러가 있으면 입력창으로 돌려줌
            return "item/itemForm";
        }
//        만약 첫번째 사진이 없고 ItemFormDto.에 아이디가 빈값이면
        if(multipartFiles.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage","첫번째 상품 이미지는 필수 입니다.");
            return "item/itemForm";
        }
        try {
            itemService.saveItem(itemFormDto,multipartFiles);

        } catch (IOException e) {
            model.addAttribute("errorMessage","상품 등록중에 오류 발생");
            return "item/itemForm";
        }
//        "redirect:/" 정상적으로 실행되면 루트로 보내준다.
        return "redirect:/";
    }

//    날릴때 데이터의 무결성을 보장하는것이 중요하다. -> Join걸어서 엉뚱한 데이터 가 들어오는것을 막아야된다.


}
