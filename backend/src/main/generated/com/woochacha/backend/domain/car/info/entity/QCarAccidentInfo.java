package com.woochacha.backend.domain.car.info.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarAccidentInfo is a Querydsl query type for CarAccidentInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarAccidentInfo extends EntityPathBase<CarAccidentInfo> {

    private static final long serialVersionUID = -1778780288L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarAccidentInfo carAccidentInfo = new QCarAccidentInfo("carAccidentInfo");

    public final QAccidentType accidentType;

    public final com.woochacha.backend.domain.car.detail.entity.QCarDetail carDetail;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QCarAccidentInfo(String variable) {
        this(CarAccidentInfo.class, forVariable(variable), INITS);
    }

    public QCarAccidentInfo(Path<? extends CarAccidentInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarAccidentInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarAccidentInfo(PathMetadata metadata, PathInits inits) {
        this(CarAccidentInfo.class, metadata, inits);
    }

    public QCarAccidentInfo(Class<? extends CarAccidentInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accidentType = inits.isInitialized("accidentType") ? new QAccidentType(forProperty("accidentType")) : null;
        this.carDetail = inits.isInitialized("carDetail") ? new com.woochacha.backend.domain.car.detail.entity.QCarDetail(forProperty("carDetail"), inits.get("carDetail")) : null;
    }

}

