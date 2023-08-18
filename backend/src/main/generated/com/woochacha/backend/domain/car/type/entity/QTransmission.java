package com.woochacha.backend.domain.car.type.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTransmission is a Querydsl query type for Transmission
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTransmission extends EntityPathBase<Transmission> {

    private static final long serialVersionUID = 247643809L;

    public static final QTransmission transmission = new QTransmission("transmission");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final EnumPath<TransmissionList> name = createEnum("name", TransmissionList.class);

    public QTransmission(String variable) {
        super(Transmission.class, forVariable(variable));
    }

    public QTransmission(Path<? extends Transmission> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTransmission(PathMetadata metadata) {
        super(Transmission.class, metadata);
    }

}

