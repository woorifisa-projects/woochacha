package com.woochacha.backend.domain.member.entity.cartrade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "car_status")
// 게시글 상태 정보 엔티티
public class CarStatus {
    @Id @GeneratedValue
    @Column(name = "car_status_id")
    private short id;

    @NotNull
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "carStatus")
    private List<SaleForm> saleForms = new ArrayList<>();

}
