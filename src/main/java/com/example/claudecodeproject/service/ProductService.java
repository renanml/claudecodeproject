package com.example.claudecodeproject.service;

import com.example.claudecodeproject.dto.PageResponse;
import com.example.claudecodeproject.dto.ProductListResponse;
import com.example.claudecodeproject.dto.ProductRequest;
import com.example.claudecodeproject.dto.ProductResponse;
import com.example.claudecodeproject.model.Category;
import com.example.claudecodeproject.model.MediaSet;
import com.example.claudecodeproject.model.Price;
import com.example.claudecodeproject.model.Product;
import com.example.claudecodeproject.repository.CategoryRepository;
import com.example.claudecodeproject.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    public PageResponse<ProductListResponse> findAll(Pageable pageable) {
        return PageResponse.from(repository.findAllProjected(pageable));
    }

    public ProductResponse findById(Long id) {
        return repository.findByIdFetched(id)
                .map(ProductResponse::from)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public ProductResponse create(ProductRequest request) {
        Product product = new Product();
        applyFields(product, request);
        return ProductResponse.from(repository.save(product));
    }

    public ProductResponse update(Long id, ProductRequest request) {
        Product product = repository.findByIdFetched(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        applyFields(product, request);
        return ProductResponse.from(repository.save(product));
    }

    private void applyFields(Product product, ProductRequest request) {
        product.setName(request.name());
        product.setDescription(request.description());

        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        if (product.getPrice() == null) {
            product.setPrice(new Price());
        }
        product.getPrice().setSalePrice(request.price().salePrice());
        product.getPrice().setListPrice(request.price().listPrice());

        if (request.mediaSet() != null) {
            if (product.getMediaSet() == null) {
                product.setMediaSet(new MediaSet());
            }
            product.getMediaSet().setThumbnail(request.mediaSet().thumbnail());
            product.getMediaSet().setMedium(request.mediaSet().medium());
            product.getMediaSet().setLarge(request.mediaSet().large());
        } else {
            product.setMediaSet(null);
        }
    }
}
