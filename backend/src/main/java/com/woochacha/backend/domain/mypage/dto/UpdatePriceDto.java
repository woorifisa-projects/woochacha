package com.woochacha.backend.domain.mypage.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdatePriceDto {

    // 유효성 처리를 위해 Dto객체 생성
    @NotNull(message = "필수 입력 값입니다.")
    @Min(value = 0, message = "가격은 0보다 큰 값이어야 합니다.")
    private Integer updatePrice; // 수정할 가격

    public UpdatePriceDto() {}

    public UpdatePriceDto(Integer updatePrice) {
        this.updatePrice = updatePrice;
    }
}
