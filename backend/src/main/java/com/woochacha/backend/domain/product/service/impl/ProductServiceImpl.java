package com.woochacha.backend.domain.product.service.impl;

import com.woochacha.backend.domain.product.dto.ProdcutResponseDto;
import com.woochacha.backend.domain.product.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {


    @Override
    public ProdcutResponseDto findAllProduct() {
        /*
            product.price
            product.status = 2
            product.car_num = car_detail.car_num
            car_detail.year
            car_detail.distance
            product.id = car_image.product_id
         */

        return null;
    }
}
