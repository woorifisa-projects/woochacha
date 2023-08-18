package com.woochacha.backend.domain.car.type.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFuel is a Querydsl query type for Fuel
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFuel extends EntityPathBase<Fuel> {

    private static final long serialVersionUID = -183620749L;

    public static final QFuel fuel = new QFuel("fuel");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final EnumPath<FuelList> name = createEnum("name", FuelList.class);

    public QFuel(String variable) {
        super(Fuel.class, forVariable(variable));
    }

    public QFuel(Path<? extends Fuel> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFuel(PathMetadata metadata) {
        super(Fuel.class, metadata);
    }

}

