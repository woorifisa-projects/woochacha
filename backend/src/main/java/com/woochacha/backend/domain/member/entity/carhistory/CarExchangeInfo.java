package com.woochacha.backend.domain.member.entity.carhistory;

import com.woochacha.backend.domain.member.entity.carinfo.CarDetail;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "car_exchange_info")
// 차량 교체이력 저장 엔티티
public class CarExchangeInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_id")
    private PartType partType;

    @CreationTimestamp
    @NotNull
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_num")
    private CarDetail carDetail;

}
