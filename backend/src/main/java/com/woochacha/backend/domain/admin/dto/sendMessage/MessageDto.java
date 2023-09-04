package com.woochacha.backend.domain.admin.dto.sendMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MessageDto {
    private String to;
    private String content; // 프로젝트에서는 비밀번호 찾기 시 난수를 반환해주는 방식이므로 내용은 body에 받아오지 않는다.
}
