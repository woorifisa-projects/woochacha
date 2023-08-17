package com.woochacha.backend.domain.member.entity;

import com.woochacha.backend.domain.member.entity.User.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "purchase_form")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 구매 신청 요청 정보 엔티티
public class PurchaseForm {
    @Id @GeneratedValue
    @Column(name = "purchase_form_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private LocalDateTime created_at;

    @NotNull
    private Boolean status;


}
