package com.homework.travel.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;

/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<Users> {

    private static final long serialVersionUID = 223140815L;

    public static final QUser user = new QUser("user");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Travel, QTravel> travels = this.<Travel, QTravel>createList("travels", Travel.class,
            QTravel.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUser(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}
