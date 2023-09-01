package com.woochacha.backend.domain.admin.service.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.domain.admin.dto.manageMember.EditMemberRequestDto;
import com.woochacha.backend.domain.admin.dto.manageMember.MemberInfoListResponseDto;
import com.woochacha.backend.domain.admin.service.ManageMemberService;
import com.woochacha.backend.domain.member.entity.QMember;
import com.woochacha.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageMemberServiceImpl implements ManageMemberService {

    private final MemberRepository memberRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public QueryResults<MemberInfoListResponseDto> getAllMemberInfo(Pageable pageable) {
        QMember sf = QMember.member;

        return jpaQueryFactory
                .select(Projections.constructor(
                        MemberInfoListResponseDto.class,
                        sf.id,
                        sf.email,
                        sf.name,
                        sf.phone,
                        sf.isAvailable
                ))
                .from(sf)
                .where(
                        sf.id.ne(1L),
                        sf.status.eq((byte) 1)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
    }

    @Override
    @Transactional
    public String updateMemberInfo(Long memberId, EditMemberRequestDto editMemberRequestDto) {
        int updatedCount = 0;
        String baseUrl = "https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/default";
        if(editMemberRequestDto.getIsChecked() == 1){
            updatedCount += memberRepository.updateMemberImage(memberId,baseUrl);
        }
        updatedCount += memberRepository.updateMemberStatus(memberId,editMemberRequestDto.getStatus());
        if (updatedCount > 0) {
            // 업데이트가 성공적으로 이루어진 경우의 처리
            return "Member status updated successfully.";
        } else {
            // 업데이트 대상이 없거나 실패한 경우의 처리
           return "No member with the specified ID and status found for update.";
        }
    }

    @Override
    @Transactional
    public String deleteMember(Long memberId) {
        Byte isAvailable = 1;
        int deleteCount = memberRepository.deleteMember(memberId,isAvailable);
        if (deleteCount > 0) {
            // isAvailable 업데이트가 성공적으로 이루어진 경우의 처리
            return "Member delete successfully.";
        } else {
            // 제거 대상이 없거나 실패한 경우의 처리
            return "No member with the specified ID and isAvailable found for delete.";
        }
    }


}
