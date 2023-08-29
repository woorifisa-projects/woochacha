package com.woochacha.backend.domain.car.type.dto;

import lombok.Data;

@Data
public class ModelDto {
    private int id;
    private String name;

    public ModelDto() {}

    public ModelDto(int id) {
        this.id = id;
    }
}
