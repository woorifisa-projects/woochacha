package com.woochacha.backend.domain.car.type.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QModel is a Querydsl query type for Model
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QModel extends EntityPathBase<Model> {

    private static final long serialVersionUID = -1390991092L;

    public static final QModel model = new QModel("model");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final EnumPath<ModelList> name = createEnum("name", ModelList.class);

    public QModel(String variable) {
        super(Model.class, forVariable(variable));
    }

    public QModel(Path<? extends Model> path) {
        super(path.getType(), path.getMetadata());
    }

    public QModel(PathMetadata metadata) {
        super(Model.class, metadata);
    }

}

