package com.woochacha.backend.domain.car.info.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPartType is a Querydsl query type for PartType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPartType extends EntityPathBase<PartType> {

    private static final long serialVersionUID = -728398210L;

    public static final QPartType partType = new QPartType("partType");

    public final NumberPath<Short> id = createNumber("id", Short.class);

    public final EnumPath<PartTypeList> type = createEnum("type", PartTypeList.class);

    public QPartType(String variable) {
        super(PartType.class, forVariable(variable));
    }

    public QPartType(Path<? extends PartType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPartType(PathMetadata metadata) {
        super(PartType.class, metadata);
    }

}

