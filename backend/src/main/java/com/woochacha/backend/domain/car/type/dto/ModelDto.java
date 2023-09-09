package com.woochacha.backend.domain.car.type.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ModelDto {
    private int id;
    private String name;

    public ModelDto() {}

    public ModelDto(int id) {
        this.id = id;
    }
}
