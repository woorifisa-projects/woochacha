package com.woochacha.backend.domain.AmazonS3.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AmazonS3RequestDto {
    MultipartFile multipartFile;
    String email;
}
