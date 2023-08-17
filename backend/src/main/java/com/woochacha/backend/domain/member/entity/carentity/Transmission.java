package com.woochacha.backend.domain.member.entity.carentity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "transmission")
// 차량 변속기 카테고리 정보 엔티티
public class Transmission {
    @Id @GeneratedValue
    @Column(name = "transmission_id")
    private Integer id;

    @NotNull
    private String name;
}