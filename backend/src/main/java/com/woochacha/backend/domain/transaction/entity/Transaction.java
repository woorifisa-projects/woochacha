package com.woochacha.backend.domain.transaction.entity;

import com.woochacha.backend.domain.purchase.entity.PurchaseForm;
import com.woochacha.backend.domain.sale.entity.SaleForm;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@Table(name = "transaction")
// 성사된 거래 정보 관리 엔티티
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private SaleForm saleForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private PurchaseForm purchaseForm;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
