package com.woochacha.backend.domain.car.info.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccidentType is a Querydsl query type for AccidentType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccidentType extends EntityPathBase<AccidentType> {

    private static final long serialVersionUID = 1243366938L;

    public static final QAccidentType accidentType = new QAccidentType("accidentType");

    public final NumberPath<Short> id = createNumber("id", Short.class);

    public final EnumPath<AccidentTypeList> type = createEnum("type", AccidentTypeList.class);

    public QAccidentType(String variable) {
        super(AccidentType.class, forVariable(variable));
    }

    public QAccidentType(Path<? extends AccidentType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccidentType(PathMetadata metadata) {
        super(AccidentType.class, metadata);
    }

}

