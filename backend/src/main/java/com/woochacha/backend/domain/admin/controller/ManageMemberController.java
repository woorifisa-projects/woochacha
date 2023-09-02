package com.woochacha.backend.domain.admin.controller;


import com.woochacha.backend.domain.admin.dto.manageMember.EditMemberRequestDto;
import com.woochacha.backend.domain.admin.dto.manageMember.MemberInfoListResponseDto;
import com.woochacha.backend.domain.admin.service.ManageMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/members")
public class ManageMemberController {
    private final ManageMemberService manageMemberService;

    // 관리자 페이지에서 사용자 관리시 모든 member의 정보를 가지고 온다.
    @GetMapping
    public ResponseEntity<Page<MemberInfoListResponseDto>> getMemberInfoList(Pageable pageable){
        List<MemberInfoListResponseDto> memberInfo = manageMemberService.getAllMemberInfo(pageable).getResults();
        return ResponseEntity.ok(new PageImpl<>(memberInfo, pageable, memberInfo.size()));
    }

    @PatchMapping("/edit/{memberId}")
    public ResponseEntity<String> editMemberInfo(@PathVariable Long memberId, @RequestBody EditMemberRequestDto editMemberRequestDto){
        String result = manageMemberService.updateMemberInfo(memberId, editMemberRequestDto);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/delete/{memberId}")
    public ResponseEntity<String> deleteMemberInfo(@PathVariable Long memberId){
        String result = manageMemberService.deleteMember(memberId);
        return ResponseEntity.ok(result);
    }
}