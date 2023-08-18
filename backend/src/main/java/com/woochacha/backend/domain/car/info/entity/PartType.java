package com.woochacha.backend.domain.car.info.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Table(name = "part_type")
// 차량 교체부위 종류 저장 엔티티
public class PartType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Enumerated(value = EnumType.STRING)
    @NotBlank
    private PartTypeList type;

}
