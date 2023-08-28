package com.woochacha.backend.domain.mypage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditProductDto {

    private String title;
    private String image;
    private Integer price;

    public EditProductDto(String title, String image, Integer price) {
        this.title = title;
        this.image = image;
        this.price = price;
    }
}
