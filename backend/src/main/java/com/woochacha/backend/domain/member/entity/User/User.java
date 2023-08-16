package com.woochacha.backend.domain.member.entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woochacha.backend.domain.member.entity.PurchaseForm;
import com.woochacha.backend.domain.member.entity.SaleForm;
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
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Embedded
    private UserInfo userInfo;

    private LocalDateTime create_at;
    private LocalDateTime update_at;

    private short is_available;
    private boolean status;
    private String profile_image;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<SaleForm> saleForms = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<PurchaseForm> purchaseForms = new ArrayList<>();

}
