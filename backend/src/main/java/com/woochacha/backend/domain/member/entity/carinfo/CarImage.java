package com.woochacha.backend.domain.member.entity.carinfo;

import com.woochacha.backend.domain.member.entity.cartrade.Product;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "car_image")
// 차량 이미지 URL 데이터 저장 엔티티
public class CarImage {
    @Id @GeneratedValue
    @Column(name = "car_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private String image_url;

    @NotNull
    private LocalDateTime created_at;

}
