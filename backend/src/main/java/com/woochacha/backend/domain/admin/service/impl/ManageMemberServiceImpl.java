package com.woochacha.backend.domain.admin.service.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.domain.admin.dto.manageMember.*;
import com.woochacha.backend.domain.admin.exception.AdminNotFound;
import com.woochacha.backend.domain.admin.repository.ManageProductFormRepository;
import com.woochacha.backend.domain.admin.service.ManageMemberService;
import com.woochacha.backend.domain.member.entity.QMember;
import com.woochacha.backend.domain.member.repository.MemberRepository;
import com.woochacha.backend.domain.product.repository.ProductRepository;
import com.woochacha.backend.domain.purchase.repository.PurchaseFormRepository;
import com.woochacha.backend.domain.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.woochacha.backend.common.DataMasking;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManageMemberServiceImpl implements ManageMemberService {

    private final MemberRepository memberRepository;
    private final JPAQueryFactory jpaQueryFactory;
    private final ProductRepository productRepository;
    private final PurchaseFormRepository purchaseFormRepository;
    private final TransactionRepository transactionRepository;
    private final ManageProductFormRepository manageProductFormRepository;
    private final DataMasking dataMasking;
    private static final Logger logger = LoggerFactory.getLogger(ManageMemberServiceImpl.class);

    @Override
    public Page<MemberInfoListResponseDto> getAllMemberInfo(Pageable pageable) {
        try {
            QMember m = QMember.member;
            QueryResults<MemberInfoListResponseDto> queryResults = jpaQueryFactory
                    .select(Projections.constructor(
                            MemberInfoListResponseDto.class,
                            m.id,
                            m.email,
                            m.name,
                            m.phone,
                            m.isAvailable
                    ))
                    .from(m)
                    .where(
                            m.id.ne(1L),
                            m.status.eq((byte) 1)
                    )
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetchResults();
            for (MemberInfoListResponseDto memberInfoDto : queryResults.getResults()){
                memberInfoDto.setPhone(dataMasking.decoding(memberInfoDto.getPhone()));
            }
            return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
        } catch (Exception e) {
            throw new AdminNotFound();
        }
    }

    @Override
    public MemberInfoResponseDto getMemberInfo(Long memberId) {
        try {
            MemberInfoDto memberInfo = memberRepository.getMemberInfo(memberId);
            memberInfo.setPhone(dataMasking.decoding(memberInfo.getPhone()));
            int countOnSale = productRepository.countSale(memberId, (short) 4);
            int countCompleteSale = productRepository.countSale(memberId, (short) 5);
            int countOnPurchase = purchaseFormRepository.countPurchaseFormId(memberId);
            int countCompletePurchase = transactionRepository.countCompletePurchase(memberId);
            System.out.println(memberInfo.toString());
            return MemberInfoResponseDto.builder()
                    .memberInfoDto(memberInfo)
                    .onSale(countOnSale)
                    .completeSale(countCompleteSale)
                    .onPurchase(countOnPurchase)
                    .completePurchase(countCompletePurchase)
                    .build();
        } catch (Exception e) {
            throw new AdminNotFound();
        }

    }

    @Override
    @Transactional
    public String updateMemberInfo(Long memberId, EditMemberRequestDto editMemberRequestDto) {
        int updatedCount = 0;
        String baseUrl = "https://woochacha.s3.ap-northeast-2.amazonaws.com/profile/default";
        if (editMemberRequestDto.getIsChecked() == 1) {
            updatedCount += memberRepository.updateMemberImage(memberId, baseUrl);
        }
        updatedCount += memberRepository.updateMemberStatus(memberId, editMemberRequestDto.getIsAvailable());
        if (updatedCount > 0) {
            // 업데이트가 성공적으로 이루어진 경우의 처리
            logger.info("memberId:{} 사용자 정보 업데이트", memberId);
            return "Member status updated successfully.";
        } else {
            // 업데이트 대상이 없거나 실패한 경우의 처리
            return "No member with the specified ID and status found for update.";
        }
    }

    @Override
    @Transactional
    public String deleteMember(Long memberId) {
        Byte status = 0;
        int deleteCount = memberRepository.deleteMember(memberId, status);
        if (deleteCount > 0) {
            // isAvailable 업데이트가 성공적으로 이루어진 경우의 처리
            logger.info("memberID:{} 사용자 삭제", memberId);
            return "Member delete successfully.";
        } else {
            // 제거 대상이 없거나 실패한 경우의 처리
            return "No member with the specified ID and isAvailable found for delete.";
        }
    }

    @Override
    public Page<MemberLogDto> getMemberLog(Long memberId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<MemberLogDto> memberLogDto = manageProductFormRepository.findAllMemberLog(memberId, pageable);



        return memberLogDto;
    }
}
