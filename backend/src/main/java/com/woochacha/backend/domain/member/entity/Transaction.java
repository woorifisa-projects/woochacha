package com.woochacha.backend.domain.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
// 성사된 거래 정보 관리 엔티티
public class Transaction {
    @Id @GeneratedValue
    @Column(name = "transaction_id")
    private Long id;

    private LocalDateTime created_at;

}
