package com.woochacha.backend.domain.car.type.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TypeDto {
    private int id;
    private String name;

    public TypeDto() {}

    public TypeDto(int id) { this.id = id; }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false; // 2번
//        TypeDto typeDto = (TypeDto) o; // 3번
//        return Objects.equals(getId(), typeDto.getId())
//                && Objects.equals(getName(), typeDto.getName());
//    }
}
