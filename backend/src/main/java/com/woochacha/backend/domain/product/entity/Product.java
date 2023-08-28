package com.woochacha.backend.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woochacha.backend.domain.car.detail.entity.CarDetail;
import com.woochacha.backend.domain.sale.entity.SaleForm;
import com.woochacha.backend.domain.status.entity.CarStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicInsert
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 게시글 정보 관리 엔티티
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private SaleForm saleForm;

    @NotNull
    private Integer price;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private CarStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_num")
    private CarDetail carDetail;

    private Integer updatePrice;

    @OneToMany(mappedBy = "product") // carImage 엔티티와의 양방향 관계 설정
    private List<CarImage> carImages = new ArrayList<>();
}
