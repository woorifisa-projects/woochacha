package com.woochacha.backend.domain.sale.entity;


import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.status.entity.CarStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@Table(name = "sale_form")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
// 판매 신청 폼 정보 관리 엔티티
public class SaleForm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String carNum;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
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

    public SaleForm(String carNum, Member member, Branch branch, CarStatus carStatus) {
        this.carNum = carNum;
        this.member = member;
        this.branch = branch;
        this.carStatus = carStatus;
    }
}
