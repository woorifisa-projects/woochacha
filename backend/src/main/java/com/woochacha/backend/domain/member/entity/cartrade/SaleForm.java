package com.woochacha.backend.domain.member.entity.cartrade;

import com.woochacha.backend.domain.member.entity.branch.Branch;
import com.woochacha.backend.domain.member.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "sale_form")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 판매 신청 폼 정보 관리 엔티티
public class SaleForm {

    @Id @GeneratedValue
    @Column(name = "sale_form_id")
    private Long id;

    @NotNull
    private String car_num;
    @NotNull
    private LocalDateTime created_at;
    @NotNull
    private LocalDateTime updated_at;

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