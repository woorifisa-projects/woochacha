package com.woochacha.backend.domain.sale.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BranchDto {
    private Long id;
    private String name;

    public BranchDto() {}

    public BranchDto(Long id) {
        this.id = id;
    }
}
