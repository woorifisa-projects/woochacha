package com.woochacha.backend.domain.member.entity.carentity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "color")
// 차량 색상 카테고리 정보 저장 엔티티
public class Color {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private ColorList name;
}
