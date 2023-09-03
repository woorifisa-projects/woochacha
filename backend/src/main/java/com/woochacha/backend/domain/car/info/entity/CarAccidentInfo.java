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
@Table(name = "car_accident_info")
// 차량 사고이력 저장 엔티티
public class CarAccidentInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accident_id")
    private AccidentType accidentType;

    @CreationTimestamp
    @NotNull
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_num")
    private CarDetail carDetail;

    public CarAccidentInfo(Integer id, AccidentType accidentType, Date createdAt, CarDetail carDetail) {
        this.id = id;
        this.accidentType = accidentType;
        this.createdAt = createdAt;
        this.carDetail = carDetail;
    }
}
