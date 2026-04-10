package com.example.claudecodeproject.controller;

import com.example.claudecodeproject.dto.CategoryRequest;
import com.example.claudecodeproject.dto.CategoryResponse;
import com.example.claudecodeproject.dto.PageResponse;
import com.example.claudecodeproject.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<CategoryResponse> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.findAll(PageRequest.of(page - 1, size));
    }

    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CategoryRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        return service.update(id, request);
    }
}
