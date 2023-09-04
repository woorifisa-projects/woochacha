package com.woochacha.backend.domain.admin.repository;

import com.woochacha.backend.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ManageProductFormRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p.id, CONCAT(CAST(cd.model.name AS string), ' ', cd.carName.name, ' ', CAST(cd.year AS string), '년형') AS title, " +
            "m.name, m.phone, p.status.id " +
            "FROM Product p " +
            "JOIN p.carDetail cd " +
            "JOIN p.saleForm sf " +
            "JOIN sf.member m " +
            "WHERE p.status.id IN (6, 9) " +
            "ORDER BY p.updatedAt ASC ")
    Page<Object[]> getDeleteEditForm(Pageable pageable);
}
