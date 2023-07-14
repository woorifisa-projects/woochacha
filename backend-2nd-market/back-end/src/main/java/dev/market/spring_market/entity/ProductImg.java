package dev.market.spring_market.entity;

import dev.market.spring_market.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "product_image")
@NoArgsConstructor
public class ProductImg extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long productImageId;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;


    @Column(name = "product_image")
    private String productImage;

    @Builder
    public ProductImg(Long productImageId, Product product, String productImage) {
        super(1);
        this.productImageId = productImageId;
        this.product = product;
        this.productImage = productImage;
    }
}
