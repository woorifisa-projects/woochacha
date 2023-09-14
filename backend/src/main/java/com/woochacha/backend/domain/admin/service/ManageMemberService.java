package com.woochacha.backend.domain.admin.service;

import com.woochacha.backend.domain.admin.dto.manageMember.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManageMemberService {
    Page<MemberInfoListResponseDto> getAllMemberInfo(Pageable pageable);
    MemberInfoResponseDto getMemberInfo(Long memberId);
    String updateMemberInfo(Long memberId, EditMemberRequestDto editMemberRequestDto);
    String deleteMember(Long memberId);
    Page<MemberLogDto> getMemberLog(Long memberId, int pageNumber, int pageSize);
}
