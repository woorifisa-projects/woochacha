package com.woochacha.backend.domain.car.detail.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCarName is a Querydsl query type for CarName
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarName extends EntityPathBase<CarName> {

    private static final long serialVersionUID = 1197706539L;

    public static final QCarName carName = new QCarName("carName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QCarName(String variable) {
        super(CarName.class, forVariable(variable));
    }

    public QCarName(Path<? extends CarName> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCarName(PathMetadata metadata) {
        super(CarName.class, metadata);
    }

}

