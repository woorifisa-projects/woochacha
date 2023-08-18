package com.woochacha.backend.domain.status.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCarStatus is a Querydsl query type for CarStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarStatus extends EntityPathBase<CarStatus> {

    private static final long serialVersionUID = -1211145429L;

    public static final QCarStatus carStatus = new QCarStatus("carStatus");

    public final NumberPath<Short> id = createNumber("id", Short.class);

    public final EnumPath<CarStatusList> status = createEnum("status", CarStatusList.class);

    public QCarStatus(String variable) {
        super(CarStatus.class, forVariable(variable));
    }

    public QCarStatus(Path<? extends CarStatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCarStatus(PathMetadata metadata) {
        super(CarStatus.class, metadata);
    }

}

