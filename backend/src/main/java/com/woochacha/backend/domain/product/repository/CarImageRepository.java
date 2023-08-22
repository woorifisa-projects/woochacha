package com.woochacha.backend.domain.product.repository;

import com.woochacha.backend.domain.product.entity.CarImage;
import com.woochacha.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarImageRepository extends JpaRepository<CarImage, Long> {
}