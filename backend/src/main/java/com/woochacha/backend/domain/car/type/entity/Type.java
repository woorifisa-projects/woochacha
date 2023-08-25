package com.woochacha.backend.domain.car.type.entity;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "type")
// 차종 카테고리 정보 저장 엔티티
public class Type {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private TypeList name;
}
