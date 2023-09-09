package com.woochacha.backend.domain.car.type.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ColorDto {
    private int id;
    private String name;

    public ColorDto() {}

    public ColorDto(int id) {this.id = id;}
}
