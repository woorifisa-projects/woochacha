package com.woochacha.backend.domain.member.entity.cartrade;

import com.woochacha.backend.domain.member.entity.carinfo.CarDetail;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 게시글 정보 관리 엔티티
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private  SaleForm saleForm;

    @NotNull
    private Integer price;

    @CreationTimestamp
    @NotNull
    private LocalDateTime created_at;

    @UpdateTimestamp
    @NotNull
    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status")
    private CarStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_num")
    private CarDetail carDetail;

}
