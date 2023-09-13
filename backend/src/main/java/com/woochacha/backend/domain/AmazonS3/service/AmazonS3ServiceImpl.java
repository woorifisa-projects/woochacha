package com.woochacha.backend.domain.AmazonS3.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.woochacha.backend.domain.AmazonS3.dto.AmazonS3ProductRequestDto;
import com.woochacha.backend.domain.AmazonS3.dto.AmazonS3RequestDto;
import com.woochacha.backend.domain.admin.dto.RegisterInputDto;
import com.woochacha.backend.domain.admin.service.impl.RegisterProductServiceImpl;
import com.woochacha.backend.domain.car.detail.entity.QCarDetail;
import com.woochacha.backend.domain.member.entity.QMember;
import com.woochacha.backend.domain.product.entity.CarImage;
import com.woochacha.backend.domain.product.entity.Product;
import com.woochacha.backend.domain.product.entity.QProduct;
import com.woochacha.backend.domain.product.repository.CarImageRepository;
import com.woochacha.backend.domain.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PersistenceUnit;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {

    private final Logger logger = LoggerFactory.getLogger(AmazonS3ServiceImpl.class);
    private final AmazonS3 amazonS3;

    private final ProductRepository productRepository;

    private final CarImageRepository carImageRepository;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final String BASE_URL = "https://woochacha.s3.ap-northeast-2.amazonaws.com";

    private final JPAQueryFactory queryFactory;

    public AmazonS3ServiceImpl(JPAQueryFactory queryFactory, AmazonS3 amazonS3, ProductRepository productRepository, CarImageRepository carImageRepository) {
        this.amazonS3 = amazonS3;
        this.queryFactory = queryFactory;
        this.productRepository = productRepository;
        this.carImageRepository = carImageRepository;
    }

    @Override
    public String uploadProfile(AmazonS3RequestDto amazonS3RequestDto) throws IOException {
        String url = this.uploadToS3("/profile", amazonS3RequestDto.getEmail(), amazonS3RequestDto.getMultipartFile());
        this.saveProfileImageToDB(amazonS3RequestDto.getEmail(), url);
        logger.debug("imageUrl:{} 프로필 이미지 업로드", url);
        return url;
    }

    @Override
    public boolean uploadProductImage(AmazonS3ProductRequestDto amazonS3ProductRequestDto) throws IOException {
        List<MultipartFile> multipartFileList = amazonS3ProductRequestDto.getMultipartFileList();
        int count = 0;

        String carNum = amazonS3ProductRequestDto.getCarNum();

        try {
            Long productId = queryFactory
                    .select(QProduct.product.id)
                    .from(QProduct.product)
                    .join(QCarDetail.carDetail)
                    .on(QProduct.product.carDetail.carNum.eq(QCarDetail.carDetail.carNum))
                    .where(QCarDetail.carDetail.carNum.eq(carNum))
                    .fetchOne();

            if (productId == null) {
                throw new Exception("찾는 차량 번호가 없습니다.");
            }

            for (MultipartFile multipartFile : multipartFileList) {
                String url = this.uploadToS3("/product/" + carNum, String.valueOf(++count), multipartFile);
                this.saveProductImageToDB(productId, url);
                logger.info("carNum:{} 차량 매물 등록 완료 imageUrl:{}", carNum, url);
            }
            return true;
        } catch (Exception e) {
            logger.debug("carNum:{} 매뭉 등록 이미지 업로드 중 에러 발생", carNum);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean uploadProductImage(RegisterInputDto registerInputDto){
        List<MultipartFile> multipartFileList = registerInputDto.getImageUrls();
        int count = 0;

        String carNum = registerInputDto.getCarNum();

        try {
            Long productId = queryFactory
                    .select(QProduct.product.id)
                    .from(QProduct.product)
                    .join(QCarDetail.carDetail)
                    .on(QProduct.product.carDetail.carNum.eq(QCarDetail.carDetail.carNum))
                    .where(QCarDetail.carDetail.carNum.eq(carNum))
                    .fetchOne();

            if (productId == null) {
                throw new Exception("찾는 차량 번호가 없습니다.");
            }

            for (MultipartFile multipartFile : multipartFileList) {
                String url = this.uploadToS3("/product/" + carNum, String.valueOf(++count), multipartFile);
                logger.info("[url] : " + url);
                this.saveProductImageToDB(productId, url);
                logger.info("carNum:{} 차량 매물 등록 완료 imageUrl:{}", carNum, url);
            }
            return true;
        } catch (Exception e) {
            logger.debug("carNum:{} 매뭉 등록 이미지 업로드 중 에러 발생", carNum);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String removeFile(Long id) throws IOException {
        return null;
    }

    public String uploadToS3(String folderName, String fileName, MultipartFile multipartFile) throws IOException {
        InputStream file = multipartFile.getInputStream();

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setCacheControl("no-cached, no-store, must-revalidate");
            metadata.setContentType("image/png");
            PutObjectRequest request = new PutObjectRequest(bucket + folderName, fileName, file, metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(request);

            return BASE_URL + folderName + "/" + fileName;

        } catch (AmazonClientException e) {
            logger.info("S3에 이미지 업로드중 에러 발생 folderName:{} fileName:{}", folderName, fileName);
            e.printStackTrace();
            return "false";
        }
    }

    public ResponseEntity<Boolean> saveProfileImageToDB(String email, String url) {
        try {
            long clause = queryFactory
                    .update(QMember.member)
                    .set(QMember.member.profileImage, url)
                    .where(QMember.member.email.eq(email))
                    .execute();
            logger.info("S3에서 RDS로 프로필 이미지 저장 imageUrl:{}", url);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            logger.error("프로필 이미지 저장 중 에러 발생 imageUrl:{}", url);
            return ResponseEntity.ok(false);
        }
    }

    // [JPQL]
//    @Transactional
//    @Modifying
//    @Query(value = "insert into car_image (product_id, image_url) values (:productId, :url)", nativeQuery = true)
//    public void saveProductImageToDB(@Param("product_id") Long productId, @Param("url") String url) {
    public void saveProductImageToDB(Long productId, String url) {
        Product product = productRepository.getReferenceById(productId);

        CarImage carImage = CarImage.builder()
                .product(product)
                .imageUrl(url)
                .build();

        carImageRepository.save(carImage);
        logger.debug("S3에서 RDS로 프로필 이미지 저장 imageUrl:{} productId:{}", url, productId);
        // [QueryDSL]

//            queryFactory
//                    .insert(QCarImage.carImage)
//                    .columns(QCarImage.carImage.product.id, QCarImage.carImage.imageUrl)
//                    .values(productId, url)
//                    .execute();

//            return ResponseEntity.ok(true);

    }
}