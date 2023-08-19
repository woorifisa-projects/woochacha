package com.woochacha.backend.domain.car.type.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Table(name = "model")
// 차량 브랜드/제조사 카테고리 정보 저장 엔티티
public class Model {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    @NotBlank
    private ModelList name;
}