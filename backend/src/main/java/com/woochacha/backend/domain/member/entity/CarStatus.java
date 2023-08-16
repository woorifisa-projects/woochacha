package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
// 게시글 상태 정보 엔티티
public class CarStatus {
    @Id @GeneratedValue
    @Column(name = "car_status_id")
    private short id;

    private String status;

}
