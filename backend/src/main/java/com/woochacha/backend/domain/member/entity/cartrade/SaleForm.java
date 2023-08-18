package com.woochacha.backend.domain.member.entity.cartrade;

import com.woochacha.backend.domain.member.entity.branch.Branch;
import com.woochacha.backend.domain.member.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "sale_form")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 판매 신청 폼 정보 관리 엔티티
public class SaleForm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String car_num;

    @CreationTimestamp
    @NotNull
    private LocalDateTime created_at;

    @UpdateTimestamp
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
