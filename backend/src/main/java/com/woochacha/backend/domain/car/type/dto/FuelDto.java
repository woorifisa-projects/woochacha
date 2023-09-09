package com.woochacha.backend.domain.car.type.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class FuelDto {
    private int id;
    private String name;

    public FuelDto() {}

    public FuelDto(int id) {
        this.id = id;
    }
}
