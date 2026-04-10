package com.example.claudecodeproject.service;

import com.example.claudecodeproject.dto.PriceRequest;
import com.example.claudecodeproject.dto.PriceResponse;
import com.example.claudecodeproject.repository.PriceRepository;
import org.springframework.stereotype.Service;

@Service
public class PriceService {

    private final PriceRepository repository;

    public PriceService(PriceRepository repository) {
        this.repository = repository;
    }

    public PriceResponse findById(Long id) {
        return repository.findByIdFetched(id)
                .map(PriceResponse::from)
                .orElseThrow(() -> new RuntimeException("Price not found"));
    }

    public PriceResponse findByProductId(Long productId) {
        return repository.findByProductId(productId)
                .map(PriceResponse::from)
                .orElseThrow(() -> new RuntimeException("Price not found for product"));
    }

    public PriceResponse update(Long id, PriceRequest request) {
        var price = repository.findByIdFetched(id)
                .orElseThrow(() -> new RuntimeException("Price not found"));
        price.setSalePrice(request.salePrice());
        price.setListPrice(request.listPrice());
        return PriceResponse.from(repository.save(price));
    }
}
