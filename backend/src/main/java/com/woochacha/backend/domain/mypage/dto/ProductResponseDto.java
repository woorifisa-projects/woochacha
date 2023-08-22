package com.woochacha.backend.domain.mypage.dto;

import com.woochacha.backend.domain.sale.entity.Branch;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;

@Data
public class ProductResponseDto {

    private String carName;
    private String imageUrl;
    private Integer price;
    private Short year;
    private Integer distance;
    private Branch branch;
    private LocalDateTime createdAt;

    public ProductResponseDto(String carName, String imageUrl, Integer price, Short year, Integer distance, Branch branch, LocalDateTime createdAt) {
        this.carName = carName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.year = year;
        this.distance = distance;
        this.branch = branch;
        this.createdAt = createdAt;
    }
}
