package kr.gradle.demo.item.dto;


import kr.gradle.demo.item.entity.ItemImage;
import lombok.*;
import org.modelmapper.ModelMapper;


//ToString 에서 List로 데려오는곳에서 순환참조가 걸릴수있다.
@Getter
@Setter
@NoArgsConstructor
public class ItemImgDto {

    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto of(ItemImage itemImg) {
        return modelMapper.map(itemImg,ItemImgDto.class);
    }
}
