package com.woochacha.backend.domain.car.info.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "exchange_type")
// 차량 교체부위 종류 저장 엔티티
public class ExchangeType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private ExchangeTypeList type;

}
