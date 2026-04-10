package com.example.claudecodeproject.controller;

import com.example.claudecodeproject.dto.PageResponse;
import com.example.claudecodeproject.dto.ProductListResponse;
import com.example.claudecodeproject.dto.ProductRequest;
import com.example.claudecodeproject.dto.ProductResponse;
import com.example.claudecodeproject.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<ProductListResponse> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.findAll(PageRequest.of(page - 1, size));
    }

    @GetMapping("/{id}")
    public ProductResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return service.update(id, request);
    }
}
