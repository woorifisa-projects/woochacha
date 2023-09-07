package com.woochacha.backend.domain.product.entity;

import com.woochacha.backend.domain.car.detail.entity.CarDetail;
import com.woochacha.backend.domain.sale.entity.SaleForm;
import com.woochacha.backend.domain.status.entity.CarStatus;
import lombok.*;
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
@Builder
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
    @Builder.Default
    private List<CarImage> carImages = new ArrayList<>();

    public Product(Long id, SaleForm saleForm, Integer price, LocalDateTime createdAt, LocalDateTime updatedAt, CarStatus status, CarDetail carDetail, Integer updatePrice, List<CarImage> carImages) {
        this.id = id;
        this.saleForm = saleForm;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.carDetail = carDetail;
        this.updatePrice = updatePrice;
        this.carImages = carImages;
    }

    public Product(Long id, SaleForm saleForm, Integer price, CarStatus status, CarDetail carDetail, List<CarImage> carImages) {
        this.id = id;
        this.saleForm = saleForm;
        this.price = price;
        this.status = status;
        this.carDetail = carDetail;
        this.carImages = carImages;
    }
}
