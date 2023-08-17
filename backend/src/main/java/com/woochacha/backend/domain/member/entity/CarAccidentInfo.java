package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "car_accident_info")
// 차량 사고이력 저장 엔티티
public class CarAccidentInfo {
    @Id @GeneratedValue
    @Column(name = "car_accident_info_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accident_id")
    private AccidentType accidentType;

    @NotNull
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_num")
    private CarDetail carDetail;
}
