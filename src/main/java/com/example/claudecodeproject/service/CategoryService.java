package com.example.claudecodeproject.service;

import com.example.claudecodeproject.dto.CategoryRequest;
import com.example.claudecodeproject.dto.CategoryResponse;
import com.example.claudecodeproject.dto.PageResponse;
import com.example.claudecodeproject.model.Category;
import com.example.claudecodeproject.repository.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public PageResponse<CategoryResponse> findAll(Pageable pageable) {
        return PageResponse.from(repository.findAll(pageable).map(CategoryResponse::from));
    }

    public CategoryResponse findById(Long id) {
        return repository.findById(id)
                .map(CategoryResponse::from)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public CategoryResponse create(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.name());
        category.setDescription(request.description());
        return CategoryResponse.from(repository.save(category));
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(request.name());
        category.setDescription(request.description());
        return CategoryResponse.from(repository.save(category));
    }
}
