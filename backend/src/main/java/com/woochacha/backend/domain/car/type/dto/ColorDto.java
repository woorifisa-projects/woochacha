package com.woochacha.backend.domain.car.type.dto;

import lombok.Data;

@Data
public class ColorDto {
    private int id;
    private String name;

    public ColorDto() {}

    public ColorDto(int id) {
        this.id = id;
    }
}
