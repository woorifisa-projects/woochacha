package com.woochacha.backend.domain.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 구매 신청 요청 정보 엔티티
public class PurchaseForm {
    @Id @GeneratedValue
    @Column(name = "purchase_form_id")
    private Long id;

    private LocalDateTime created_at;

    private Boolean status;


}
