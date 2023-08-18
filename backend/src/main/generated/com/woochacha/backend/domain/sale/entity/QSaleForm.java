package com.woochacha.backend.domain.sale.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSaleForm is a Querydsl query type for SaleForm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSaleForm extends EntityPathBase<SaleForm> {

    private static final long serialVersionUID = 828021787L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSaleForm saleForm = new QSaleForm("saleForm");

    public final QBranch branch;

    public final StringPath carNum = createString("carNum");

    public final com.woochacha.backend.domain.status.entity.QCarStatus carStatus;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.woochacha.backend.domain.member.entity.QMember member;

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QSaleForm(String variable) {
        this(SaleForm.class, forVariable(variable), INITS);
    }

    public QSaleForm(Path<? extends SaleForm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSaleForm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSaleForm(PathMetadata metadata, PathInits inits) {
        this(SaleForm.class, metadata, inits);
    }

    public QSaleForm(Class<? extends SaleForm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.branch = inits.isInitialized("branch") ? new QBranch(forProperty("branch")) : null;
        this.carStatus = inits.isInitialized("carStatus") ? new com.woochacha.backend.domain.status.entity.QCarStatus(forProperty("carStatus")) : null;
        this.member = inits.isInitialized("member") ? new com.woochacha.backend.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

