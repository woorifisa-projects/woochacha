package com.woochacha.backend.domain.AmazonS3.controller;

import com.woochacha.backend.domain.AmazonS3.dto.AmazonS3ProductRequestDto;
import com.woochacha.backend.domain.AmazonS3.dto.AmazonS3RequestDto;
import com.woochacha.backend.domain.AmazonS3.service.AmazonS3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

//@Transactional // JPA에서 update/delete 쿼리를 사용하기 위해 처리해줘야 함
@RestController
@RequestMapping("/s3")
public class AmazonS3Controller {

    public final AmazonS3Service amazonS3Service;

    public AmazonS3Controller(AmazonS3Service amazonS3Service) {
        this.amazonS3Service = amazonS3Service;
    }

    @Transactional
    @PostMapping("/upload-profile")
    public String uploadProfile(@ModelAttribute AmazonS3RequestDto amazonS3RequestDto) throws IOException {
        return amazonS3Service.uploadProfile(amazonS3RequestDto);
    }

    @PostMapping("/upload-product")
    public boolean uploadProfile(@ModelAttribute AmazonS3ProductRequestDto amazonS3ProductRequestDto) throws IOException {
        return amazonS3Service.uploadProductImage(amazonS3ProductRequestDto);
    }

}
