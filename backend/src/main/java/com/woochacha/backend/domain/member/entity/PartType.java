package com.woochacha.backend.domain.member.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
// 차량 교체부위 종류 저장 엔티티
public class PartType {
    @Id @GeneratedValue
    @Column(name = "part_type_id")
    private short id;

    private String type;

}
