package com.woochacha.backend.domain.admin.dto.manageMember;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetMemberDto {
    private Byte isAvailable;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private String profileImage;

    public GetMemberDto(Byte isAvailable, String name, String email, String phone, LocalDateTime createdAt, String profileImage) {
        this.isAvailable = isAvailable;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.profileImage = profileImage;
    }
}
