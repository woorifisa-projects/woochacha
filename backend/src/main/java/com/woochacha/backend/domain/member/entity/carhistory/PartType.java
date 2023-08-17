package com.woochacha.backend.domain.member.entity.carhistory;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter
@Table(name = "part_type")
// 차량 교체부위 종류 저장 엔티티
public class PartType {
    @Id @GeneratedValue
    @Column(name = "part_type_id")
    private short id;
    @NotNull
    private String type;

}
