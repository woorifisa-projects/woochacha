package com.woochacha.backend.domain.admin.dto;

import lombok.Getter;
import java.util.List;

// 차량 등록 게시글 등록 전 정보를 보여주는 화면 Dto
@Getter
public class RegisterProductDto {

    private String title; // 제목: 브랜드+차량명+년형
    private String carNum; // 차량 번호
    private String ownerName; // 소유주명
    private String ownerPhone;
    private Integer distance;
    private short year;
    private short capacity;
    private String type;
    private String model;
    private String fuel;
    private String color;
    private String transmission;
    private String branch;
    private List<String> accidentHistory; // 사고 종류 (내역 + 날짜), 날짜 최신순
    private List<String> exchangeHistory; // 교체 부위 (내역 + 날짜), 날짜 최신순
}
