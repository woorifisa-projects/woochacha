package com.woochacha.backend.domain.product.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@Builder
@AllArgsConstructor
@Table(name = "car_image")
// 차량 이미지 URL 데이터 저장 엔티티
public class CarImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotBlank
    private String imageUrl;

    @CreationTimestamp
//    @NotBlank
    private LocalDateTime createdAt;

    public CarImage() {

    }
}
