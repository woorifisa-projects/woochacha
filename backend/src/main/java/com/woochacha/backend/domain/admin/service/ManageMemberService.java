package com.woochacha.backend.domain.admin.service;

import com.querydsl.core.QueryResults;
import com.woochacha.backend.domain.admin.dto.manageMember.EditMemberRequestDto;
import com.woochacha.backend.domain.admin.dto.manageMember.MemberInfoListResponseDto;
import org.springframework.data.domain.Pageable;

public interface ManageMemberService {
    QueryResults<MemberInfoListResponseDto> getAllMemberInfo(Pageable pageable);
    String updateMemberInfo(Long memberId, EditMemberRequestDto editMemberRequestDto);
    String deleteMember(Long memberId);
}
