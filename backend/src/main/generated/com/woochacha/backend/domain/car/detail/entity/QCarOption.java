package com.woochacha.backend.domain.car.detail.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarOption is a Querydsl query type for CarOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarOption extends EntityPathBase<CarOption> {

    private static final long serialVersionUID = -12553451L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarOption carOption = new QCarOption("carOption");

    public final BooleanPath airbag = createBoolean("airbag");

    public final BooleanPath blackbox = createBoolean("blackbox");

    public final QCarDetail carDetail;

    public final BooleanPath heatedSeat = createBoolean("heatedSeat");

    public final BooleanPath highPass = createBoolean("highPass");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final BooleanPath navigation = createBoolean("navigation");

    public final BooleanPath rearviewCamera = createBoolean("rearviewCamera");

    public final BooleanPath smartKey = createBoolean("smartKey");

    public final BooleanPath sunroof = createBoolean("sunroof");

    public QCarOption(String variable) {
        this(CarOption.class, forVariable(variable), INITS);
    }

    public QCarOption(Path<? extends CarOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarOption(PathMetadata metadata, PathInits inits) {
        this(CarOption.class, metadata, inits);
    }

    public QCarOption(Class<? extends CarOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.carDetail = inits.isInitialized("carDetail") ? new QCarDetail(forProperty("carDetail"), inits.get("carDetail")) : null;
    }

}

