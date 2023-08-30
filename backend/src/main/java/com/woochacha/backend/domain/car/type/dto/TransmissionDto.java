package com.woochacha.backend.domain.car.type.dto;

import lombok.Data;

@Data
public class TransmissionDto {
    private int id;
    private String name;

    public TransmissionDto() {}

    public TransmissionDto(int id) {
        this.id = id;
    }
}
