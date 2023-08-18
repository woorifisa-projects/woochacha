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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private PartTypeList type;

}
