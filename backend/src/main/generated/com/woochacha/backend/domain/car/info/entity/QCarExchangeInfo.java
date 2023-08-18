package com.woochacha.backend.domain.car.info.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarExchangeInfo is a Querydsl query type for CarExchangeInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarExchangeInfo extends EntityPathBase<CarExchangeInfo> {

    private static final long serialVersionUID = -286389868L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarExchangeInfo carExchangeInfo = new QCarExchangeInfo("carExchangeInfo");

    public final com.woochacha.backend.domain.car.detail.entity.QCarDetail carDetail;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QPartType partType;

    public QCarExchangeInfo(String variable) {
        this(CarExchangeInfo.class, forVariable(variable), INITS);
    }

    public QCarExchangeInfo(Path<? extends CarExchangeInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarExchangeInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarExchangeInfo(PathMetadata metadata, PathInits inits) {
        this(CarExchangeInfo.class, metadata, inits);
    }

    public QCarExchangeInfo(Class<? extends CarExchangeInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.carDetail = inits.isInitialized("carDetail") ? new com.woochacha.backend.domain.car.detail.entity.QCarDetail(forProperty("carDetail"), inits.get("carDetail")) : null;
        this.partType = inits.isInitialized("partType") ? new QPartType(forProperty("partType")) : null;
    }

}

