package com.woochacha.backend.domain.car.info.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "exchange_type")
@NoArgsConstructor
// 차량 교체부위 종류 저장 엔티티
public class ExchangeType {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    @Column(name = "type")
    private ExchangeTypeList type;

    public ExchangeType(short id, ExchangeTypeList type) {
        this.id = id;
        this.type = type;
    }
}
