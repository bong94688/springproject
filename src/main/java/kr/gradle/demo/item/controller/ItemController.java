package kr.gradle.demo.item.controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import kr.gradle.demo.item.dto.ItemFormDto;
import kr.gradle.demo.item.dto.ItemSearchDto;
import kr.gradle.demo.item.entity.Item;
import kr.gradle.demo.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

//   변수로 받기위해 /다음에 {itemId}명시
    @GetMapping("/admin/item/{itemId}")
    public String itemDetail(@PathVariable("itemId")Long itemId , Model model){

        try{

            ItemFormDto itemFormDto = itemService.getItemDetail(itemId);
            model.addAttribute("ItemFormDto",itemFormDto);
//            return"/item/itemForm";
        }
        catch (EntityNotFoundException e){
            model.addAttribute("errorMessage","존재하지 않는 상품입니다.");
            model.addAttribute("ItemFormDto",new ItemFormDto());
            return "/item/itemForm";
        }

        return "/item/itemForm";
    }
    @PostMapping("/admin/item/{itemId}")
//    @Valid  값을 Dto에 넣어놨기 때문에 벨리드 어노테이션을 붙여주고 바인딩
//    bindingResult 바인딩 결과 받아오고, => 잘못된 경우 Model에 에러메시지 보냄
    public String itemUpdate(@Valid ItemFormDto itemFormDto,BindingResult bindingResult,
                             Model model,@RequestParam("itemImgFile")List<MultipartFile> multipartFiles){

        if(bindingResult.hasErrors()){
//            만약 바인딩에 에러가 있어?? -> 있으면 처리 로직
            return "item/itemForm";
        }
        //        만약 첫번째 사진이 없고 ItemFormDto.에 아이디가 빈값이면
        if(multipartFiles.get(0).isEmpty() && itemFormDto.getId() == null){
            model.addAttribute("errorMessage","첫번째 상품 이미지는 필수 입니다.");
            return "item/itemForm";
        }
        try {
            System.out.println(itemFormDto);
            itemService.updateItem(itemFormDto,multipartFiles);

        } catch (IOException e) {
            model.addAttribute("errorMessage","상품 수정중에 오류가 발생했습니다.");
            return "item/itemForm";
        }


//       root로 되돌리고 싶을때 리다이렉트 해서 새로고침!
        return "redirect:/";
    }
//    검색조건 페이징 과 겟 연결 동시 처리
    @GetMapping({"/admin/items","/admin/items/{page}"})
//    뷰단에서 itemSearchDto 을받고
    public String itemList(ItemSearchDto itemSearchDto, Model model
            ,
//                           "/admin/items/{page}" 이런 url에 get 방식으로 접근이 올때 빈값이 올수있기때문에 옵션얼로 받아 쓴다.
                           @PathVariable("page")Optional<Integer> page){
//        PageRequest 에서 쓰는 of라는 메소드을 써서 page 정보를 만든다.
//         한페이지당  0번부터 3개씩
//        page.isPresent()?page.get():0,5 페이지 정보에 만약 정보가 있냐 ? null 이 아닌가? 널이 아니면 page 정보를 가져오고 널이면 0 을 가져온다.
        Pageable pageable = PageRequest.of(page.isPresent()?page.get():0,5);
//        페이지로 묶여있는 Item을 서비스단을 쓰기위해서
        Page<Item> items = itemService.getAdminItemPage(itemSearchDto,pageable);


//        페이징 묶은 객체를 뷰에 items으로 넘겨준다.
        model.addAttribute("items",items);
//        itemSearchDto을 쓰고싶으면 쓰게 만들기위해 itemSearchDto 던져준다.
        model.addAttribute("itemSearchDto",itemSearchDto);
//      우리가 볼 페이징을 할때 한 페이지에 볼 페이지 개수를 5 개로 만들겠다 뷰에 알려준다. 예를들어 1,2,3,4,5 같은 묶음으로 보여줘야되서 만드는것! 6,7,8,9,10
//        ? 아닐수도 있겠다?
        model.addAttribute("maxPage",6);



        return "item/itemList";
    }

//    아이템 이미지 수정
//    @PostMapping("/admin/item/{itemId}")
//    public


}
