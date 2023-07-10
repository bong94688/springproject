package kr.gradle.demo.item.service;

import kr.gradle.demo.item.Repository.ItemImgRepository;
import kr.gradle.demo.item.entity.ItemImage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@Service
//Autowired를 final로 올리면 저절로 Autowired 되는 어노테이션
@RequiredArgsConstructor
// image가 저장되던중 실패하면 다 취소하는 어노테이션
@Transactional
public class ItemImgService {

//    springproperties의 String 형태로 가져온다.
    @Value(value = "${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    void saveItemImg(ItemImage itemImage, MultipartFile itemImgFile) throws IOException {
//      MultipartFile -> FileService -> 로컬 저장 로직

//        멀티파트 파일에 오리진 파일을 가져오고
        String oriImgName = itemImgFile.getOriginalFilename();
//       파일서비스에서 만든다.
        String imgName ="";
//       이미지 Url -> 파일 서비스 처리
        String imgUrl ="";

//       실제이름이 비어있는가??? 없으면 처리
        if(!StringUtils.isEmpty(oriImgName))
        {
//           fileService 에 실제 로컬에 저장되는 로직 -> 저장될 경로,멀티파트 파일에
//           실제 경로와 멀티파트 파일에 실제 이름을 보내서 DB에 저장된 랜덤으로 만든 이름을 가져온다.
            imgName = fileService.uploadFile(itemImgLocation,oriImgName,itemImgFile.getBytes());
//          실제 웹에서 접근할 url -> WebMvcConfig 세팅값
            imgUrl = "/images/item/"+imgName;
        }

//        public void updateItemImage(String oriImgName, String imgName, String imgUrl) {
//            this.imgName = imgName;
//            this.oriImgName = oriImgName;
//            this.imgUrl = imgUrl;
//        }
//       itemImage라는 변수안에 실제 저장된 값들을 넣어주는 로직
        itemImage.updateItemImage(oriImgName,imgName,imgUrl);

        itemImgRepository.save(itemImage);


    }
}
