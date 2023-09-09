package com.woochacha.backend.domain.car.detail.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class CarNameDto {
    private Long id;
    private String name;

    public CarNameDto() {}

    public CarNameDto(Long id) { this.id = id; }
}
