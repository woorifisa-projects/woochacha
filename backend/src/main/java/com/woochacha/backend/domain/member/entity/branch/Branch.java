package com.woochacha.backend.domain.member.entity.branch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "branch")
// 차고지 정보 엔티티
public class Branch {
    @Id @GeneratedValue
    @Column(name = "branch_id")
    private Long id;

    @NotNull
    private String name;

}
