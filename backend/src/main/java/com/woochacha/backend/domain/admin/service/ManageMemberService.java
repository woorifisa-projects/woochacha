package com.woochacha.backend.domain.admin.service;

import com.querydsl.core.QueryResults;
import com.woochacha.backend.domain.admin.dto.manageMember.EditMemberRequestDto;
import com.woochacha.backend.domain.admin.dto.manageMember.MemberInfoListResponseDto;
import com.woochacha.backend.domain.admin.dto.manageMember.MemberInfoResponseDto;
import com.woochacha.backend.domain.admin.dto.manageMember.MemberLogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ManageMemberService {
    QueryResults<MemberInfoListResponseDto> getAllMemberInfo(Pageable pageable);
    MemberInfoResponseDto getMemberInfo(Long memberId);
    String updateMemberInfo(Long memberId, EditMemberRequestDto editMemberRequestDto);
    String deleteMember(Long memberId);
    public Page<MemberLogDto> getMemberLog(Long memberId, int pageNumber, int pageSize);
}
