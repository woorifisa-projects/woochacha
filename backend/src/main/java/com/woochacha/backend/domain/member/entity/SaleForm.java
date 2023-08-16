package com.woochacha.backend.domain.member.entity;

import com.woochacha.backend.domain.member.entity.User.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 판매 신청 폼 정보 관리 엔티티
public class SaleForm {

    @Id @GeneratedValue
    @Column(name = "sale_form_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;



}
