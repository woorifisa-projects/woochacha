package com.woochacha.backend.domain.member.entity.carentity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "transmission")
// 차량 변속기 카테고리 정보 엔티티
public class Transmission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private TransmissionList name;
}
