package com.woochacha.backend.domain.member.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
// 차량 이미지 URL 데이터 저장 엔티티
public class CarImage {
    @Id @GeneratedValue
    @Column(name = "car_image_id")
    private Long id;

    private String image_url;

    private LocalDateTime created_at;

}
