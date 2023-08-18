package com.woochacha.backend.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarImage is a Querydsl query type for CarImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCarImage extends EntityPathBase<CarImage> {

    private static final long serialVersionUID = -892168985L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarImage carImage = new QCarImage("carImage");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final QProduct product;

    public QCarImage(String variable) {
        this(CarImage.class, forVariable(variable), INITS);
    }

    public QCarImage(Path<? extends CarImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarImage(PathMetadata metadata, PathInits inits) {
        this(CarImage.class, metadata, inits);
    }

    public QCarImage(Class<? extends CarImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

