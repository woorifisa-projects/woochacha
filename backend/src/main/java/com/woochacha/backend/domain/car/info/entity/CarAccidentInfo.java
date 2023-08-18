package com.woochacha.backend.domain.car.info.entity;

import com.woochacha.backend.domain.car.detail.entity.CarDetail;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@Table(name = "car_accident_info")
// 차량 사고이력 저장 엔티티
public class CarAccidentInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accident_id")
    private AccidentType accidentType;

    @CreationTimestamp
    @NotBlank
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_num")
    private CarDetail carDetail;
}
