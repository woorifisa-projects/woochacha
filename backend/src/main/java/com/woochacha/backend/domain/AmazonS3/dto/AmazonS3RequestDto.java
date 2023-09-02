package com.woochacha.backend.domain.AmazonS3.dto;

import lombok.Data;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Data
@Setter
public class AmazonS3RequestDto {
    MultipartFile multipartFile;
    String email;
}
