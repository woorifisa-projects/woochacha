package com.woochacha.backend.domain.member.entity.cartrade;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "transaction")
// 성사된 거래 정보 관리 엔티티
public class Transaction {
    @Id @GeneratedValue
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private SaleForm saleForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private PurchaseForm purchaseForm;

    @NotNull
    private LocalDateTime created_at;
}
