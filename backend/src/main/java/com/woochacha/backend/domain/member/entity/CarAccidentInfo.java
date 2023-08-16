package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
// 차량 사고이력 저장 엔티티
public class CarAccidentInfo {
    @Id @GeneratedValue
    @Column(name = "car_accident_info_id")
    private int id;

    private LocalDateTime created_at;
}
