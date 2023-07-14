package dev.market.spring_market.service;

import dev.market.spring_market.dto.ProductImgRequest;
import dev.market.spring_market.entity.Product;
import dev.market.spring_market.entity.ProductImg;
import dev.market.spring_market.repository.ProductImgRepo;
import dev.market.spring_market.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dev.market.spring_market.dto.ProductImgResponse;
import dev.market.spring_market.dto.ProductRequest;
import dev.market.spring_market.dto.ProductResponse;
import dev.market.spring_market.repository.CategoryRepo;
import dev.market.spring_market.repository.UserRepo;

@Service

public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final ProductImgRepo productImgRepo;
    private final UserRepo userRepo;


    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo, ProductImgRepo productImgRepo, UserRepo userRepo) {
		super();
		this.productRepo = productRepo;
		this.categoryRepo = categoryRepo;
        this.productImgRepo = productImgRepo;
		this.userRepo = userRepo;
	}

	public List<ProductImgResponse> getProductImgList(Product p) {
        List<ProductImgResponse> productImgDTOS = p.getProductImages().stream()
                .map(ProductImgResponse::from)
                .collect(Collectors.toList());
        return productImgDTOS;
    }

    @Override
    public List<ProductResponse> findAll() {
        List<ProductResponse> productDTOS = new ArrayList<>();

        Iterable<Product> products = productRepo.findAll();

        for(Product p : products) {
            List<ProductImgResponse> productImgDTOS = getProductImgList(p);

            // Product -> ProductDTO 변환
            ProductResponse productDTO = ProductResponse.builder()
                    .title(p.getTitle())
                    .price(p.getPrice())
                    .createdAt(p.getCreatedAt())
                    .productImages(productImgDTOS)
                    .categoryId(p.getCategory().getCategoryId())
                    .build();

            // productDTOS.add()
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    @Override
    public ProductResponse findById(Long productId) {
        Product p = productRepo.getReferenceById(productId);

        List<ProductImgResponse> productImgDTOS = getProductImgList(p);

        return ProductResponse.builder()
                .title(p.getTitle()).price(p.getPrice())
                .contents(p.getContents()).createdAt(p.getCreatedAt())
                .productImages(productImgDTOS)
                .categoryId(p.getCategory().getCategoryId())
                .build();
    }

	@Override
	public void save(ProductRequest productRequest) {
		Product product = Product.builder()
					.user(userRepo.getReferenceById(productRequest.getUserId()))
					.title(productRequest.getTitle())
					.price(productRequest.getPrice())
					.contents(productRequest.getContents())
					.category(categoryRepo.getReferenceById(productRequest.getCategoryId()))
					.build();

		Product p = productRepo.save(product);


        List<ProductImgRequest> productImgRequests = productRequest.getProductImgRequests();

        for(ProductImgRequest pr : productImgRequests) {
            ProductImg productImg = ProductImg.builder()
                    .product(p)
                    .productImage(pr.getProductImage())
                    .build();
            productImgRepo.save(productImg);
        }
    }


    @Override
    public void update(Long id, ProductRequest productRequest) {
        Product oldProduct = productRepo.getReferenceById(id);

//        List<ProductImgRequest> productImgRequests = productRequest.getProductImgRequests();
//        List<ProductImg> productImages = new ArrayList<>();
//
//        for(ProductImgRequest pr : productImgRequests) {
//            Long imgId = productImgRepo.findByProductImage(pr.getProductImage());
//
//            ProductImg productImg = ProductImg.builder()
//                    .productImageId(imgId)
//                    .product(oldProduct)
//                    .productImage(pr.getProductImage())
//                    .build();
//
//            productImages.add(productImg);
//        }


        Product newProduct = new Product(oldProduct.getCreatedAt(),
                    oldProduct.getStatus(),
                    id,
                    categoryRepo.getReferenceById(productRequest.getCategoryId()),
                    userRepo.getReferenceById(productRequest.getUserId()),
                    productRequest.getTitle(),
                    productRequest.getPrice(),
                    productRequest.getContents()
                );

        productRepo.save(newProduct);
    }

    @Override
    public void delete(Long id) {
        Product oldProduct = productRepo.getReferenceById(id);

        Product newProduct = new Product(oldProduct.getCreatedAt(),
                0,
                id,
                categoryRepo.getReferenceById(oldProduct.getCategory().getCategoryId()),
                userRepo.getReferenceById(oldProduct.getUser().getUserId()),
                oldProduct.getTitle(),
                oldProduct.getPrice(),
                oldProduct.getContents()
        );

        productRepo.save(newProduct);

    }
}
