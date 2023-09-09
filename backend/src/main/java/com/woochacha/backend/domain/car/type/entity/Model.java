package com.woochacha.backend.domain.car.type.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "model")
// 차량 브랜드/제조사 카테고리 정보 저장 엔티티
public class Model {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @Column(name = "name")
    private ModelList name;

    public Model(Integer id, ModelList name) {
        this.id = id;
        this.name = name;
    }
}
