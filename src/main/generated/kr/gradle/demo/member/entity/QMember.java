package kr.gradle.demo.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1839112892L;

    public static final QMember member = new QMember("member1");

    public final kr.gradle.demo.utils.entity.QBaseEntity _super = new kr.gradle.demo.utils.entity.QBaseEntity(this);

    public final StringPath adress = createString("adress");

    //inherited
    public final StringPath createBy = _super.createBy;

    public final StringPath email = createString("email");

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath name = createString("name");

    public final StringPath passowrd = createString("passowrd");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final EnumPath<kr.gradle.demo.member.constant.Role> role = createEnum("role", kr.gradle.demo.member.constant.Role.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

