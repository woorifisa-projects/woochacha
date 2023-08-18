package com.woochacha.backend.domain.member.entity.cartrade;

import com.woochacha.backend.domain.member.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@Table(name = "purchase_form")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 구매 신청 요청 정보 엔티티
public class PurchaseForm {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreationTimestamp
    @NotBlank
    private LocalDateTime createdAt;

    @ColumnDefault("0")
    @NotBlank
    private Boolean status;
}
