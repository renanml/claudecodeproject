package com.example.claudecodeproject.controller;

import com.example.claudecodeproject.dto.PriceRequest;
import com.example.claudecodeproject.dto.PriceResponse;
import com.example.claudecodeproject.service.PriceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final PriceService service;

    public PriceController(PriceService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public PriceResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/product/{productId}")
    public PriceResponse findByProductId(@PathVariable Long productId) {
        return service.findByProductId(productId);
    }

    @PutMapping("/{id}")
    public PriceResponse update(@PathVariable Long id, @Valid @RequestBody PriceRequest request) {
        return service.update(id, request);
    }
}
