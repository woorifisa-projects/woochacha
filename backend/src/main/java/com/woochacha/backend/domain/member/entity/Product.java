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
// 게시글 정보 관리 엔티티
public class Product {
    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private LocalDateTime created_at;
    private LocalDateTime updated_at;



}
