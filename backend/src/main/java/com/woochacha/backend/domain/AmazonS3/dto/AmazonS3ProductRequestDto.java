package com.woochacha.backend.domain.AmazonS3.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class AmazonS3ProductRequestDto {
    List<MultipartFile> multipartFileList;
    String carNum;
}
