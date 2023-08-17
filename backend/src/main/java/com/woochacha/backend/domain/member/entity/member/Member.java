package com.woochacha.backend.domain.member.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woochacha.backend.domain.member.entity.cartrade.PurchaseForm;
import com.woochacha.backend.domain.member.entity.cartrade.SaleForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 유저 엔티티
@Entity
@Table(name = "member")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Embedded
    private MemberInfo memberInfo;

    private LocalDateTime create_at;
    private LocalDateTime update_at;

    private short is_available;
    private boolean status;
    private String profile_image;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<SaleForm> saleForms = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<PurchaseForm> purchaseForms = new ArrayList<>();

}
