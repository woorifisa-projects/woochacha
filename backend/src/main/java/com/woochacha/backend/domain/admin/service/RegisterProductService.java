package com.woochacha.backend.domain.admin.service;

import com.woochacha.backend.domain.admin.dto.RegisterInputDto;
import com.woochacha.backend.domain.admin.dto.RegisterProductDto;
import java.io.IOException;
import java.text.ParseException;

public interface RegisterProductService {

    // 매물 등록 전 폼 조회시 보여줄 데이터를 QLDB에서 조회
    RegisterProductDto getRegisterProductInfo(Long saleFormId);

    // 매물 등록 폼 제출을 위한 메서드
    void registerProduct(Long salFormId, RegisterInputDto registerInputDto) throws IOException, ParseException; // QLDB 조회, 이미지와 가격을 입력받아 가져오는 메서드 실행
}
