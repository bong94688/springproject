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

    public final StringPath adress = createString("adress");

    public final StringPath email = createString("email");

    public final NumberPath<Long> Id = createNumber("Id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath passowrd = createString("passowrd");

    public final EnumPath<kr.gradle.demo.member.constant.Role> role = createEnum("role", kr.gradle.demo.member.constant.Role.class);

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

