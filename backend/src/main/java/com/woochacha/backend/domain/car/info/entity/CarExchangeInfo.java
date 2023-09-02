package com.woochacha.backend.domain.car.info.entity;

import com.woochacha.backend.domain.car.detail.entity.CarDetail;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_num")
    private CarDetail carDetail;

    public CarExchangeInfo(int id, ExchangeType exchangeType, Date createdAt, CarDetail carDetail) {
        this.id = id;
        this.exchangeType = exchangeType;
        this.createdAt = createdAt;
        this.carDetail = carDetail;
    }
}
