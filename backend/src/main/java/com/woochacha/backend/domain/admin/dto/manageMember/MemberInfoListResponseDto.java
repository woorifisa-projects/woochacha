package com.woochacha.backend.domain.admin.dto.manageMember;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberInfoListResponseDto {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private Byte isAvailable;

    public MemberInfoListResponseDto(Long id, String email, String name, String phone, Byte isAvailable) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.isAvailable = isAvailable;
    }
}
