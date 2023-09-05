package com.woochacha.backend.domain.admin.dto.manageMember;

import lombok.Data;
import java.time.LocalDateTime;

// 사용사의 로그 데이터 저장을 위한 Dto
@Data
public class MemberLogDto {

    String email; // 로그 사용자 이메일
    String name; // 로그 사용자 이름
    LocalDateTime date; // 로그 발생 시점
    String description; // 로그 내용 (ex. 로그인, 로그아웃, 게시글 등록 등등)
    String etc; // 기타 부연설명

    public MemberLogDto(String email, String name, LocalDateTime date, String description, String etc) {
        this.email = email;
        this.name = name;
        this.date = date;
        this.description = description;
        this.etc = etc;
    }
}
