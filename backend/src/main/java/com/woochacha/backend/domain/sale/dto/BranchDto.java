package com.woochacha.backend.domain.sale.dto;

import lombok.Getter;

@Getter
public class BranchDto {
    private Long id;
    private String name;

    public BranchDto() {}

    public BranchDto(Long id) {
        this.id = id;
    }
}
