package kr.gradle.demo.item.service;

import jakarta.persistence.EntityNotFoundException;
import kr.gradle.demo.item.Repository.ItemImgRepository;
import kr.gradle.demo.item.Repository.ItemRepository;
import kr.gradle.demo.item.constant.ItemSellStatus;
import kr.gradle.demo.item.dto.ItemFormDto;
import kr.gradle.demo.item.entity.Item;
import kr.gradle.demo.item.entity.ItemImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImgRepository itemImgRepository;

//   임의의 멀티파트 데이터를 만든다.
    List<MultipartFile> createMultipartFiles() throws Exception{

        List<MultipartFile> multipartFileList = new ArrayList<>();
        for(int i=0;i<5;i++){
//0~5 까지 path에 접근해서
            String path = "C:/shop/item/";
//       image0~4 .jpg 생성
            String imageName = "image" + i + ".jpg";
//           Mock이라는 멀티파트 파일 테스트 데이터를생성
            MockMultipartFile multipartFile =
                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1,2,3,4});
//        임의의 데이터 리스트에 추가
            multipartFileList.add(multipartFile);
        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveItem() throws Exception {
        ItemFormDto itemFormDto = new ItemFormDto();
        itemFormDto.setItemNm("테스트상품");
        itemFormDto.setItemSellStatus(ItemSellStatus.SELL);
        itemFormDto.setItemDetail("테스트 상품 입니다.");
        itemFormDto.setPrice(1000);
        itemFormDto.setStockNumber(100);

        List<MultipartFile> multipartFileList = createMultipartFiles();
        Long itemId = itemService.saveItem(itemFormDto, multipartFileList);
        List<ItemImage> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new);

//       실제 데이터랑 찾아온 데이터랑 같은지 비교
        assertEquals(itemFormDto.getItemNm(), item.getItemNm());
        assertEquals(itemFormDto.getItemSellStatus(), item.getItemSellStatus());
        assertEquals(itemFormDto.getItemDetail(), item.getItemDetail());
        assertEquals(itemFormDto.getPrice(), item.getPrice());
        assertEquals(itemFormDto.getStockNumber(), item.getStockNumber());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), itemImgList.get(0).getOriImgName());
    }

}