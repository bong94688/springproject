package kr.gradle.demo.item.Repository;


import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.gradle.demo.item.constant.ItemSellStatus;
import kr.gradle.demo.item.dto.ItemSearchDto;
import kr.gradle.demo.item.entity.Item;
import kr.gradle.demo.item.entity.QItem;
import kr.gradle.demo.item.entity.QItemImage;
import kr.gradle.demo.main.dto.MainItemDto;
import kr.gradle.demo.main.dto.QMainItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static kr.gradle.demo.item.entity.QItem.item;

// 사용자 정의 인터페이스 구현
// 동적으로 쿼리를 처리하기위한 작업 -> 사용자 입력에따라 달리하는게 동적 쿼리 작업이라고 명명한다.


public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

//    쿼리 Dsl 의 필수 쿼리 팩토리 private으로 만들고 밑에서 생성자 주입
    private JPAQueryFactory jpaQueryFactory;

//
    public ItemRepositoryCustomImpl(EntityManager em){
//        ItemRepositoryCustomImpl 여기서 호출되면 엔티티 매니져 객체를 받아서 jpaQueryFactory에 객체를 주입시킨다
        jpaQueryFactory = new JPAQueryFactory(em);
    }

//    원하는 개수를 가져올수있는 페이징 기능 구현
    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
//        selectFrom 전체를 가져올때는 selectFrom으로 다가져온다.
//         querydsl 에서 사용하는 Q도메인 객체를 넣는다.
//        QItem객체에 안에있는 item을 꺼내 쓰고
        List<Item> list = jpaQueryFactory
                .selectFrom(item)
                .where(
//                        동적 커리
//                        ,은 and !!
//                아이템에 itemSearchDto.getSearchDateType 을 가져오는데
//                regDtsAfter 이라는 매개 함수를 작동시켜서  BooleanExpression 이라는 null 이거나 날짜 item.regTime.after(dateTime) 이라는 거
//                에서보면 뺀 날짜에서 이후 데이터들을 가져온다.
                        regDtsAfter(itemSearchDto.getSearchDateType()),
//                   searchSellStatusEq 이라는 에서 판매중인지 아닌지 상태를 받아와서
//                   return searchSellStatus == null ? null : item.itemSellStatus.eq(searchSellStatus); 을리턴해준다.
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
 //                어떤 방법을 할것인가 itemNm 으로 검색 , createBy 작성자 으로 검색으로 둘중하나 로 검색 like 검색이므로 like %검색어% 라서 검색조건안에 검색어가 포함되면 가져온다. 없으면 null
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())
                        )
//                아이템에서 아이디 순서대로 내림차순으로 만든다;.
//                QItem.item 스태틱하게 붙기때문에 QItem.item Convert to static import 으로 스태틱 영역에 올려서 item으로 쓸수 있게 만든다.
                .orderBy(item.id.desc())
//              어디부터 할거냐를 offect으로 잡을수 있다 받아온 페이지 정보에서 가져온 pageable 객체에서 옵셋을 꺼내서 쓰고 시작할 위치
                .offset(pageable.getOffset())
//               limit -> 페이지 전체 개수를 줘서 페이지가 몇개냐를 알려주는 역할
                .limit(pageable.getPageSize())
//               어차피 받을때 리스트로 뽑아오기 떄문에 fetch 의 반환타입이 list 이니까 그냥 fetch를 쓴다
                .fetch();

//        페이징 작업 querydsl
//          Wildcard.count 그룹함수 -> 가져온 행의 개수를 가져는거라 groupby 와같이 몇개 메소드가 호출됬는지 만 토탈에 담는다. COUNT 을 사용하고있어서  fetchOne 에서 에러 조건인 2개이상이나 null 조건이 발생하지 않는다.
        long total = jpaQueryFactory.select(Wildcard.count).from(item)
                .where(
//                       조건에 따라서 개수가 100개 1000개 그이상이 될수도있기때문에 와일드 카드 를 쓴다.
//                        밑조건은 어쨋든 우리의 검색 조건의 최종적으로 것들의 합으로 나오는것이므로 위에 검색조건의 where 조건과 똑같이해야된다!
                        regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .fetchOne()
                ;
//        페이지는 인터페이스라서 객체생성이 안되서 페이지를 임플한 것중에
//        PageImpl(List<T> content, Pageable pageable, long total)  이라는 구현체에서 이 메소드를 쓴다.
//         list 로 된 컨텐츠는 querydsl 검색 조건 에서 담긴 list 를 리턴하고 page 정보는
//        앞단에서 받아온 pageable 객체를 가져오고 total은 dsl을 써서 계산한다음 만들어서 리턴하는것
        return new PageImpl<>(list,pageable,total);
    }

    @Override
    public Page<MainItemDto> getMainItemDtoPage(ItemSearchDto itemSearchDto, Pageable pageable) {

//        메인에 보여줄 아이템
//        QItem을 새로생성하고 item은 이 클래스에 스태틱에 올라와서 연결되어있어서 사용가능하다.
        QItem qItem = item;

//       QItem이미지를 가져와서 qItem에연관관계가 되어잇기 때문에 qitemImage도 필요하다.
        QItemImage itemImage = QItemImage.itemImage;

//       jpaQueryFactory을 EntityManager을 주입 받았기때문에 jpaQueryfactory을 querydsl을 사용해서 조회하는 방법
        List<MainItemDto> results = jpaQueryFactory.select(
                        new QMainItemDto(
//                       먼저 item.id을 가져와야되고
                                item.id,
//                        item이름
                                item.itemNm,
//                        아이템 디테일
                                item.itemDetail,
//                        이미지 Url
                                itemImage.imgUrl,
//                        가격정보
                                item.price
//                        이들이 필요해서 각 Q클래스로 생성했던 객체들을 이용해서 하나씩 가져온다.
                        )).from(
//                       이미지에 각 정보들이 있으므로 itemImage에서 가져오는게 적절하다.
                        itemImage
                ).join(itemImage.item, item)
                .where(itemImage.repImgYn.eq("Y"))
 //                itemNmLike의 검색조건을 가져와서 빈값이면 null 아니면 Qitem 에 삽입시켜서 리턴받는다.
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
//                item에 id 내림차순으로 찾아온다.
                .orderBy(item.id.desc())
//              어디부터 할거냐를 offect으로 잡을수 있다 받아온 페이지 정보에서 가져온 pageable 객체에서 옵셋을 꺼내서 쓰고 시작할 위치
//               위에 앞에 로직에서 pageable 객체에 주입되어있는 Offeset정보을 가져오는것!
                .offset(pageable.getOffset())
//               limit -> 페이지 전체 개수를 줘서 페이지가 몇개냐를 알려주는 역할-> total 페이지 -> 먼저 선행으로 total 을 계산해서 넣어줘야지 그 페이지에 맞춰서 가져온다.
                .limit(pageable.getPageSize())
                .fetch();

//        List<MainItemDto> contents = List -> 굳이 필요없는 로직
//        리스트에 전체개수 -> total -> 와일드 카드를써서 전체개수를 가져온다
        long total = jpaQueryFactory
                .select(Wildcard.count)
                .from(itemImage)
                .join(itemImage.item, item)
                .where(itemImage.repImgYn.eq("Y"))
//                itemNmLike의 검색조건을 가져와서 빈값이면 null 아니면 Qitem 에 삽입시켜서 리턴받는다.
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
                .fetchOne()
                ;

// 위에 만든 기능 서비스에서 쓸수있다.

        return new PageImpl<>(results,pageable,total);
    }


    //   ItemSellStatus -> SELL이냐 SOLD OUT 이냐 변수로 받고
//    searchSellStatus 타입으로 리턴하고 만약 searchSellStatus null 이면 null null이 아니면 item에 상태에서 비교 하고 null 이나 item.itemSellStatus.eq(searchSellStatus) 을 리턴
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        return searchSellStatus == null ? null : item.itemSellStatus.eq(searchSellStatus);
    }

//    검색을하는데 한달전 일주일전 데이터를 찾아올수 있다.
    private BooleanExpression regDtsAfter(String searchDateType){

        LocalDateTime dateTime = LocalDateTime.now();

//        searchDateType seacrch 하는 걸 가져오는데 all 1d 1w 1m 6m으로 적혀있다.
        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
//            만약 가져온 데이터가 1d 이면 데이에서 하루을 빼고
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
//            1w 이면 1주 를빼고
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
//           1m이면 1달을 뺀다
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
//            6m 이면 6달을 빼고
            dateTime = dateTime.minusMonths(6);
        }

//       반환하는것을 등록시간을 저장되어있는 지금에서 정보를 준다.
        return item.regTime.after(dateTime);
    }

//    어떤 방법을 할것인가 itemNm 으로 검색 , createBy 작성자 으로 검색으로 둘중하나 로 검색
    private BooleanExpression searchByLike(String searchBy, String searchQuery){

        if(StringUtils.equals("itemNm", searchBy)){
//            item에서 네임을 like 검색을 실행
            return item.itemNm.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)){
//            만약 작성자라면
            return item.createBy.like("%" + searchQuery + "%");
        }

        return null;
    }
    private BooleanExpression itemNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%" + searchQuery + "%");
    }
}
