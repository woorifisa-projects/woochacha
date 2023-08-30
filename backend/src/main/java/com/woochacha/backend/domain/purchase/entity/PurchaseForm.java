package com.woochacha.backend.domain.purchase.entity;

import com.woochacha.backend.domain.member.entity.Member;
import com.woochacha.backend.domain.product.entity.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@DynamicInsert
@Table(name = "purchase_form")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 구매 신청 요청 정보 엔티티
public class PurchaseForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDate meetingDate;

    @Column(name = "status")
    @ColumnDefault("0")
    private int status;

    public PurchaseForm(Long id, Product product, Member member, LocalDateTime createdAt, LocalDate meetingDate, int status) {
        this.id = id;
        this.product = product;
        this.member = member;
        this.createdAt = createdAt;
        this.meetingDate = meetingDate;
        this.status = status;
    }
}
