package com.woochacha.backend.domain.car.info.entity;

import com.woochacha.backend.domain.car.detail.entity.CarDetail;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@Table(name = "car_exchange_info")
// 차량 교체이력 저장 엔티티
public class CarExchangeInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_id")
    private ExchangeType exchangeType;

    @CreationTimestamp
    @NotNull
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_num")
    private CarDetail carDetail;

}
