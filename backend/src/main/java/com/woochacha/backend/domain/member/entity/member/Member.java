package com.woochacha.backend.domain.member.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woochacha.backend.domain.member.entity.cartrade.PurchaseForm;
import com.woochacha.backend.domain.member.entity.cartrade.SaleForm;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 유저 엔티티
@Entity
@Getter
@DynamicInsert
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private MemberInfo memberInfo;

    @CreationTimestamp
    private LocalDateTime createAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ColumnDefault("1")
    @NotBlank
    private short isAvailable;

    @ColumnDefault("1")
    @NotBlank
    private boolean status;

    private String profileImage;
}
