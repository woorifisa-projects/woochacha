package com.woochacha.backend.domain.member.entity.CarEntity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "model")
// 차량 브랜드/제조사 카테고리 정보 저장 엔티티
public class Model {
    @Id @GeneratedValue
    @Column(name = "modle_id")
    private Integer id;

    @NotNull
    private String name;
}
