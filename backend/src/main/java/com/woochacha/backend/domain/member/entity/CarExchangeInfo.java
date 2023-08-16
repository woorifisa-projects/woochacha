package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
// 차량 교체이력 저장 엔티티
public class CarExchangeInfo {
    @Id @GeneratedValue
    @Column(name = "car_exchange_info_id")
    private int id;

    private LocalDateTime created_at;

}
