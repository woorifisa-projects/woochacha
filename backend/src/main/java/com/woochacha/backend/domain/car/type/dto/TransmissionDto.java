package com.woochacha.backend.domain.car.type.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TransmissionDto {
    private int id;
    private String name;

    public TransmissionDto() {}

    public TransmissionDto(int id) {
        this.id = id;
    }
}
