package com.woochacha.backend.domain.member.entity.CarEntity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "color")
// 차량 색상 카테고리 정보 저장 엔티티
public class Color {
    @Id @GeneratedValue
    @Column(name = "color_id")
    private Integer id;

    @NotNull
    private String name;
}
