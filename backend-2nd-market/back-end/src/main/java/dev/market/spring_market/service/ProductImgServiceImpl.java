package dev.market.spring_market.service;

import dev.market.spring_market.dto.ProductImgRequest;
import dev.market.spring_market.dto.ProductImgResponse;
import dev.market.spring_market.dto.ProductRequest;
import dev.market.spring_market.dto.ProductResponse;
import dev.market.spring_market.entity.Product;
import dev.market.spring_market.entity.ProductImg;
import dev.market.spring_market.repository.CategoryRepo;
import dev.market.spring_market.repository.ProductImgRepo;
import dev.market.spring_market.repository.ProductRepo;
import dev.market.spring_market.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ProductImgServiceImpl implements ProductImgService {

    private final ProductImgRepo productImgRepoRepo;

    public ProductImgServiceImpl(ProductImgRepo productImgRepoRepo) {
		super();
		this.productImgRepoRepo = productImgRepoRepo;
	}

//    @Override
//    public List<ProductImg> findByProductImage(List<String> productImages) {
//        for(ProductImg pi : productImages) {
//            List<ProductImg> imgIds = productImgRepoRepo.findByProductImage(pi);
//
//        }
//
//        return imgIds;
//    }
}
