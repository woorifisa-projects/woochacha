package com.woochacha.backend.domain.admin.dto.manageMember;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EditMemberRequestDto {
    private int isChecked;
    private Byte isAvailable;

    public EditMemberRequestDto() {}
    public EditMemberRequestDto(int isChecked, Byte isAvailable) {
        this.isChecked = isChecked;
        this.isAvailable = isAvailable;
    }
}
