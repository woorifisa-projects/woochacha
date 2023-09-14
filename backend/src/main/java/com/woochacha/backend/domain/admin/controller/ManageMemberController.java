package com.woochacha.backend.domain.admin.controller;


import com.woochacha.backend.domain.admin.dto.manageMember.*;
import com.woochacha.backend.domain.admin.service.ManageMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/members")
public class ManageMemberController {
    private final ManageMemberService manageMemberService;

    // 관리자 페이지에서 사용자 관리시 모든 member의 정보를 가지고 온다.
    @GetMapping
    public ResponseEntity<Page<MemberInfoListResponseDto>> getMemberInfoList(Pageable pageable){
        return ResponseEntity.ok(manageMemberService.getAllMemberInfo(pageable));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(@PathVariable Long memberId){
        MemberInfoResponseDto memberInfoResponseDto = manageMemberService.getMemberInfo(memberId);
        return ResponseEntity.ok(memberInfoResponseDto);
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

    // 로그 정보 조회
    @GetMapping("/log/{memberId}")
    public ResponseEntity<Page<MemberLogDto>> memberLogInfoList(@PathVariable("memberId") Long memberId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size){
        Page<MemberLogDto> memberLogDtos = manageMemberService.getMemberLog(memberId, page, size);
        return ResponseEntity.ok(memberLogDtos);
    }
}
