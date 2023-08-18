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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 유저 엔티티
@Entity
@Table(name = "member")
@DynamicInsert
@Getter @Setter
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
    @NonNull
    private short isAvailable;

    @ColumnDefault("1")
    @NonNull
    private boolean status;
    private String profileImage;

}
