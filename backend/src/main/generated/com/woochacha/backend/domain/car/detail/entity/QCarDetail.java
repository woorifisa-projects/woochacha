package com.woochacha.backend.domain.car.detail.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarDetail is a Querydsl query type for CarDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarDetail extends EntityPathBase<CarDetail> {

    private static final long serialVersionUID = -337640719L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarDetail carDetail = new QCarDetail("carDetail");

    public final NumberPath<Short> capacity = createNumber("capacity", Short.class);

    public final QCarName carName;

    public final StringPath carNum = createString("carNum");

    public final com.woochacha.backend.domain.car.type.entity.QColor color;

    public final NumberPath<Integer> distance = createNumber("distance", Integer.class);

    public final com.woochacha.backend.domain.car.type.entity.QFuel fuel;

    public final com.woochacha.backend.domain.car.type.entity.QModel model;

    public final StringPath owner = createString("owner");

    public final StringPath phone = createString("phone");

    public final com.woochacha.backend.domain.car.type.entity.QTransmission transmission;

    public final com.woochacha.backend.domain.car.type.entity.QType type;

    public final NumberPath<Short> year = createNumber("year", Short.class);

    public QCarDetail(String variable) {
        this(CarDetail.class, forVariable(variable), INITS);
    }

    public QCarDetail(Path<? extends CarDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarDetail(PathMetadata metadata, PathInits inits) {
        this(CarDetail.class, metadata, inits);
    }

    public QCarDetail(Class<? extends CarDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.carName = inits.isInitialized("carName") ? new QCarName(forProperty("carName")) : null;
        this.color = inits.isInitialized("color") ? new com.woochacha.backend.domain.car.type.entity.QColor(forProperty("color")) : null;
        this.fuel = inits.isInitialized("fuel") ? new com.woochacha.backend.domain.car.type.entity.QFuel(forProperty("fuel")) : null;
        this.model = inits.isInitialized("model") ? new com.woochacha.backend.domain.car.type.entity.QModel(forProperty("model")) : null;
        this.transmission = inits.isInitialized("transmission") ? new com.woochacha.backend.domain.car.type.entity.QTransmission(forProperty("transmission")) : null;
        this.type = inits.isInitialized("type") ? new com.woochacha.backend.domain.car.type.entity.QType(forProperty("type")) : null;
    }

}

