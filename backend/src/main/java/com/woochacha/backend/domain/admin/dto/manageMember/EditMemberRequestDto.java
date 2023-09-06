package com.woochacha.backend.domain.admin.dto.manageMember;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class EditMemberRequestDto {
    private int isChecked;
    private Byte isAvailable;
}
