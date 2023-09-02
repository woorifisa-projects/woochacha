package com.woochacha.backend.domain.AmazonS3.service;

import com.woochacha.backend.domain.AmazonS3.dto.AmazonS3ProductRequestDto;
import com.woochacha.backend.domain.AmazonS3.dto.AmazonS3RequestDto;
import com.woochacha.backend.domain.admin.dto.RegisterInputDto;

import java.io.IOException;

public interface AmazonS3Service {
    public String uploadProfile(AmazonS3RequestDto amazonS3RequestDto) throws IOException;

    public boolean uploadProductImage(AmazonS3ProductRequestDto amazonS3ProductRequestDto) throws IOException;
    public String removeFile(Long id) throws IOException;

    // 매물 등록시 차량 이미지, 가격을 저장할 DTO
    public boolean uploadProductImage(RegisterInputDto registerInputDto) throws IOException;


}
