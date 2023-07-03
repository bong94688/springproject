package kr.gradle.demo.item.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QItem2 is a Querydsl query type for Item2
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem2 extends EntityPathBase<Item2> {

    private static final long serialVersionUID = -2117810044L;

    public static final QItem2 item2 = new QItem2("item2");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemDetail = createString("itemDetail");

    public final StringPath itemNm = createString("itemNm");

    public final EnumPath<kr.gradle.demo.item.constant.ItemSellStatus> itemSellStatus = createEnum("itemSellStatus", kr.gradle.demo.item.constant.ItemSellStatus.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> regTime = createDateTime("regTime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> stockNumber = createNumber("stockNumber", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> updateTime = createDateTime("updateTime", java.time.LocalDateTime.class);

    public QItem2(String variable) {
        super(Item2.class, forVariable(variable));
    }

    public QItem2(Path<? extends Item2> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem2(PathMetadata metadata) {
        super(Item2.class, metadata);
    }

}

