package com.junhyeong.shoppingmall.services.product;

import com.junhyeong.shoppingmall.dtos.ProductDto;
import com.junhyeong.shoppingmall.dtos.ProductsDto;
import com.junhyeong.shoppingmall.exceptions.CategoryNotFound;
import com.junhyeong.shoppingmall.models.category.Category;
import com.junhyeong.shoppingmall.models.product.Product;
import com.junhyeong.shoppingmall.models.review.Review;
import com.junhyeong.shoppingmall.repositories.CategoryRepository;
import com.junhyeong.shoppingmall.repositories.ProductRepository;
import com.junhyeong.shoppingmall.repositories.ReviewRepository;
import com.junhyeong.shoppingmall.specifications.ProductSpecification;
import com.junhyeong.shoppingmall.specifications.ReviewSpecification;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GetProductsService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;

    public GetProductsService(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional(readOnly = true)
//    @Cacheable(value = "productsCache", key = "#page + #categoryId + #keyword", cacheManager = "redisCacheManager")
    public ProductsDto products(Integer page, Long categoryId, String keyword) {
        Pageable pageable = PageRequest.of(page - 1, 8, Sort.by("createAt").descending().and(Sort.by("id").descending()));

        Specification<Product> spec = getProductSpecification(categoryId, keyword);

        Page<Product> products = productRepository.findAll(spec, pageable);

        int totalPageCount = products.getTotalPages();

        List<ProductDto> productDtos = products.stream()
                .map((product -> {
                    Category category = categoryRepository.findById(product.categoryId())
                            .orElseThrow(CategoryNotFound::new);

                    Long totalReviewCount = totalReviewCount(product.id());

                    double totalRating = totalRating(product.id(), totalReviewCount);
                    return product.toDto(category.name(), totalRating, totalReviewCount);
                })).toList();

        return new ProductsDto(productDtos, totalPageCount);
    }

    private Specification<Product> getProductSpecification(Long categoryId, String keyword) {
        Specification<Product> spec = (root, query, builder) -> null;

        if (categoryId != 0) {
            spec = spec.and(ProductSpecification.equalCategoryId(categoryId));
        }

        if (!keyword.isBlank()) {
            spec = spec.and(ProductSpecification.likeProductName(keyword));
        }
        return spec;
    }

    private Long totalReviewCount(Long productId) {
        Specification<Review> spec = Specification.where(ReviewSpecification.equalProductId(productId));
        spec = spec.and(ReviewSpecification.isFalseDeletedStatus());
        return reviewRepository.count(spec);
    }

    public double totalRating(Long productId, Long totalReviewCount) {
        Specification<Review> spec = Specification.where(ReviewSpecification.equalProductId(productId));
        spec = spec.and(ReviewSpecification.isFalseDeletedStatus());
        List<Review> reviews = reviewRepository.findAll(spec);

        double totalRating = reviews.stream().
                mapToDouble(review -> review.rating()).
                reduce(0, (acc, rating) -> acc + rating) / totalReviewCount;

        return totalReviewCount != 0 ? totalRating : 0;
    }
}
