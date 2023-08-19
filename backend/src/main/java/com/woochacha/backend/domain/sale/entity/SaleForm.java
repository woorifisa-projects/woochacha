package com.woochacha.backend.domain.sale.entity;


import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.status.entity.CarStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@Table(name = "sale_form")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 판매 신청 폼 정보 관리 엔티티
public class SaleForm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String carNum;

    @CreationTimestamp
    @NotBlank
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @NotBlank
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private CarStatus carStatus;
}