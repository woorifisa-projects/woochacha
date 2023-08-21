package com.woochacha.backend.domain.AmazonS3.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.domain.AmazonS3.dto.AmazonS3ProductRequestDto;
import com.woochacha.backend.domain.AmazonS3.dto.AmazonS3RequestDto;
import com.woochacha.backend.domain.car.detail.entity.QCarDetail;
import com.woochacha.backend.domain.member.entity.QMember;
import com.woochacha.backend.domain.product.entity.QCarImage;
import com.woochacha.backend.domain.product.entity.QProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {

    private final Logger LOGGER = LoggerFactory.getLogger(AmazonS3ServiceImpl.class);
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    private final JPAQueryFactory queryFactory;

    public AmazonS3ServiceImpl(JPAQueryFactory queryFactory, AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
        this.queryFactory = queryFactory;
    }


    public String upload(String folderName, String fileName, MultipartFile multipartFile) throws IOException {
        InputStream file = multipartFile.getInputStream();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setCacheControl("no-cached, no-store, must-revalidate"); // 60*60*24*7 일주일
            metadata.setContentType("image/png");
            PutObjectRequest request = new PutObjectRequest(bucket + folderName, fileName, file, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(request);

            String url = "s3://" + bucket + folderName + "/" + fileName;

            return url;

        } catch (AmazonClientException e) {
            e.printStackTrace();
            return "false";
        }
    }

    public ResponseEntity<Boolean> saveProfileImage(String email, String url) {
        try {
            long clause = queryFactory
                    .update(QMember.member)
                    .set(QMember.member.profileImage, url)
                    .where(QMember.member.email.eq(email))
                    .execute();
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }


//    public void saveProductImage(@Param("productId") Long productId, @Param("url") String url) {
    @Modifying
    @Query(value = "insert into car_image (product_id, image_url) values (:productId, :url)", nativeQuery = true)
    public void saveProductImage(Long productId, String url) {
        try {
//            queryFactory
//                    .insert(QCarImage.carImage)
//                    .columns(QCarImage.carImage.product.id, QCarImage.carImage.imageUrl)
//                    .values(productId, url)
//                    .execute();

//            return ResponseEntity.ok(true);
        } catch (Exception e) {
//            return ResponseEntity.ok(false);
        }


    }

    public String uploadProfile(AmazonS3RequestDto amazonS3RequestDto) throws IOException {
        String url = this.upload("/profile", amazonS3RequestDto.getEmail(), amazonS3RequestDto.getMultipartFile());
        this.saveProfileImage(amazonS3RequestDto.getEmail(), url);
        return url;
    }

    public boolean uploadProductImage(AmazonS3ProductRequestDto amazonS3ProductRequestDto) throws IOException {
        List<MultipartFile> multipartFileList = amazonS3ProductRequestDto.getMultipartFileList();
        int count = 0;

        String carNum = amazonS3ProductRequestDto.getCarNum();

        Long productId = null;

        try {
            productId = queryFactory
                    .select(QProduct.product.id)
                    .from(QProduct.product)
                    .join(QCarDetail.carDetail)
                    .on(QProduct.product.carDetail.carNum.eq(QCarDetail.carDetail.carNum))
                    .where(QCarDetail.carDetail.carNum.eq(carNum))
                    .fetchOne();

            LOGGER.info("[product id] " + productId);

            if (productId == null) {
                throw new Exception("찾는 차량 번호가 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (MultipartFile multipartFile : multipartFileList) {
            String url = this.upload("/product/" + carNum, String.valueOf(++count), multipartFile);
            this.saveProductImage(productId, url);
        }
        return true;
    }

    @Override
    public String removeFile(Long id) throws IOException {
        return null;
    }
}