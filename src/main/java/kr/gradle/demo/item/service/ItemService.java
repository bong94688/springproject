package kr.gradle.demo.item.service;

import jakarta.persistence.EntityNotFoundException;
import kr.gradle.demo.item.Repository.ItemImgRepository;
import kr.gradle.demo.item.Repository.ItemRepository;
import kr.gradle.demo.item.dto.ItemFormDto;
import kr.gradle.demo.item.dto.ItemImgDto;
import kr.gradle.demo.item.entity.Item;
import kr.gradle.demo.item.entity.ItemImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
//Autowired를 final로 올리면 저절로 Autowired 되는 어노테이션
@RequiredArgsConstructor
// image가 저장되던중 실패하면 다 취소하는 어노테이션
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    private final ItemImgRepository itemImgRepository;

    private final ItemImgService itemImgService;

//  저장된 이미지 저장후 아이템 테이블에 아이템들 저장 -> 이미지 + 아이템 매핑해서 저장
    public Long saveItem(ItemFormDto itemFormDto, List<MultipartFile> itemImgFileList) throws IOException {

//      itemFormDto 에서 아이템 부분만 item으로 바꿔주는 로직
        Item item = itemFormDto.createItem();
//       저장을하고 아이템의 Id값을 가져오기위해 먼저 item을 저장한다.
        itemRepository.save(item);

//       파일 업로드 로직 시작!
        for(int i = 0 ; i< itemImgFileList.size(); i++){
//          ItemImage 형태의 빈값으로 itemImage을 만들어준다.
            ItemImage itemImage = new ItemImage();
//           영속성 영역에 떠있는 item을 저장 떠있는 item 과 itemImage 연관
            itemImage.setItem(item);
            if(i==0){
//               대표이미지로 쓸것인가??
                itemImage.setRepImgYn("Y");
            }else {
                itemImage.setRepImgYn("N");
            }
//          itemImgService에 만든 saveItemImg 메소드로 아이템 이미지들을 하나씩 저장
//          itemImage안에 item은 위에 setItem으로 연관지어서 멀티파트 파일과 보내고 나머지 내용은 saveItemImg안 로직에서
//           채운다!!
            itemImgService.saveItemImg(itemImage,itemImgFileList.get(i));
        }

//        itemRepository.save(item); 이 로직에서 item을 리턴하기 때문에 item객체에
//        Id값이 채워져있다. 그래서 그 Id를 리턴해주면됨!
        return item.getId();
    }

    public ItemFormDto getItemDetail(Long itemId){


//       findBy ItemId (OrderBy Id 내림차순으로)
        List<ItemImage> itemImages = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtos = new ArrayList<>();

//       itemImages 에서 itemImage를 하나씩 꺼내서 of 로 Dto로 바꿔준다.
        for(ItemImage  itemImage : itemImages){
            ItemImgDto itemImgDto = ItemImgDto.of(itemImage);
            itemImgDtos.add(itemImgDto);
        }

//       item찾기 Optional이기때문에 없으면 throw 던지는 로직 필요
        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

//       itemFormDto에 만들어놓은 of를 만들어놓는거로 바꾸는로직
        ItemFormDto itemFormDto = ItemFormDto.of(item);

//
        itemFormDto.setItemImgDtoList(itemImgDtos);
//        itemFormDto.setItemImgIds();

        return itemFormDto;
    }

}
