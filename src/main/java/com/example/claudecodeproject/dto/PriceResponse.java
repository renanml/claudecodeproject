package com.example.claudecodeproject.dto;

import com.example.claudecodeproject.model.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(
        Long id,
        BigDecimal salePrice,
        BigDecimal listPrice,
        Long productId,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static PriceResponse from(Price p) {
        return from(p, p.getProduct() != null ? p.getProduct().getId() : null);
    }

    public static PriceResponse from(Price p, Long productId) {
        return new PriceResponse(p.getId(), p.getSalePrice(), p.getListPrice(), productId, p.getCreatedAt(), p.getModifiedAt());
    }
}
