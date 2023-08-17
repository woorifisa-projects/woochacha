package com.woochacha.backend.domain.member.entity.CarEntity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "type")
// 차종 카테고리 정보 저장 엔티티
public class Type {
    @Id @GeneratedValue
    @Column(name = "type_id")
    private Integer id;

    @NotNull
    private String name;
}
