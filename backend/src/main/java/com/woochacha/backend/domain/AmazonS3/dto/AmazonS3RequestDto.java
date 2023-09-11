package com.woochacha.backend.domain.AmazonS3.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AmazonS3RequestDto {
    MultipartFile multipartFile;
    String email;

    public AmazonS3RequestDto() {}

    public AmazonS3RequestDto(MultipartFile multipartFile, String email) {
        this.multipartFile = multipartFile;
        this.email = email;
    }
}
