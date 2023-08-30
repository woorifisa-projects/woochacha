package com.woochacha.backend.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

// 차량 등록 게시글 등록 전 정보를 보여주는 화면 Dto
@Getter
@Builder
public class RegisterProductDto {

    private String title; // 제목: 브랜드+차량명+년형
    private String carNum; // 차량 번호
    private String ownerName; // 소유주명
    private String ownerPhone; // 소유주 전화번호
    private Integer distance; // 주행거리
    private int year; // 연식
    private int capacity; // 승차 정원
    private String type; // ex. 중형, 소형
    private String model; // 브랜드명
    private String fuel; // 연료
    private String color; // 차량 색상
    private String transmission; // 변속기 종류
    private String branch; // 차고지명
    private List<String> accidentHistory; // 사고 종류 (내역 + 날짜), 날짜 최신순
    private List<String> exchangeHistory; // 교체 부위 (내역 + 날짜), 날짜 최신순

    /*
    옵션 정보들
     */
    boolean heatSeatQLDB;
    boolean smartKeyQLDB;
    boolean blackboxQLDB;
    boolean navigationQLDB;
    boolean airbagQLDB;
    boolean sunroofQLDB;
    boolean highPassQLDB;
    boolean rearviewCameraQLDB;

    public RegisterProductDto(String title, String carNum, String ownerName, String ownerPhone, Integer distance, int year, int capacity, String type, String model, String fuel, String color, String transmission, String branch, List<String> accidentHistory, List<String> exchangeHistory, boolean heatSeatQLDB, boolean smartKeyQLDB, boolean blackboxQLDB, boolean naviagtionQLDB, boolean airbagQLDB, boolean sunroofQLDB, boolean highPassQLDB, boolean rearviewCameraQLDB) {
        this.title = title;
        this.carNum = carNum;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.distance = distance;
        this.year = year;
        this.capacity = capacity;
        this.type = type;
        this.model = model;
        this.fuel = fuel;
        this.color = color;
        this.transmission = transmission;
        this.branch = branch;
        this.accidentHistory = accidentHistory;
        this.exchangeHistory = exchangeHistory;
        this.heatSeatQLDB = heatSeatQLDB;
        this.smartKeyQLDB = smartKeyQLDB;
        this.blackboxQLDB = blackboxQLDB;
        this.navigationQLDB = naviagtionQLDB;
        this.airbagQLDB = airbagQLDB;
        this.sunroofQLDB = sunroofQLDB;
        this.highPassQLDB = highPassQLDB;
        this.rearviewCameraQLDB = rearviewCameraQLDB;
    }
}
