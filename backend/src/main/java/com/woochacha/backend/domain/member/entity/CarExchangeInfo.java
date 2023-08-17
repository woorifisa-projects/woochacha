package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "car_exchange_info")
// 차량 교체이력 저장 엔티티
public class CarExchangeInfo {
    @Id @GeneratedValue
    @Column(name = "car_exchange_info_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private PartType partType;

    @NotNull
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_num")
    private CarDetail carDetail;

}
