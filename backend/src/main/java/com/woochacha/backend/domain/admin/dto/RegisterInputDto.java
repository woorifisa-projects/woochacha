package com.woochacha.backend.domain.admin.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

// 매물 등록시 등록 폼에서 차량 가격, 차량 이미지 데이터를 입력받기 위한 Dto
@Data
public class RegisterInputDto {

    @Size(min = 4, max = 4, message = "사진은 4개 입력되어야 합니다.") // min, max 모두 4로 설정하여 4개의 요소가 입력되어야 함
    List<MultipartFile> imageUrls; // 사진 네개 등록 가능

    String carNum; // 차량 번호

    @NotNull(message = "필수 입력 값입니다.")
    @Min(value = 0, message = "가격은 0보다 큰 값이어야 합니다.")
    private Integer price; // 판매 가격
}
