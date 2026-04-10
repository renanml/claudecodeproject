package com.example.claudecodeproject.dto;

import com.example.claudecodeproject.model.Product;

import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        CategoryResponse category,
        PriceResponse price,
        MediaSetResponse mediaSet,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getCategory() != null ? CategoryResponse.from(p.getCategory()) : null,
                p.getPrice() != null ? PriceResponse.from(p.getPrice(), p.getId()) : null,
                p.getMediaSet() != null ? MediaSetResponse.from(p.getMediaSet()) : null,
                p.getCreatedAt(),
                p.getModifiedAt()
        );
    }
}
