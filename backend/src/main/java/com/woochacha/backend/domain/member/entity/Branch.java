package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
// 차고지 정보 엔티티
public class Branch {
    @Id @GeneratedValue
    @Column(name = "branch_id")
    private Long id;

    private String name;
}
