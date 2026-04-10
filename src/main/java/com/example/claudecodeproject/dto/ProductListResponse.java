package com.example.claudecodeproject.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductListResponse(
        Long id,
        String name,
        Long categoryId,
        String categoryName,
        BigDecimal salePrice,
        BigDecimal listPrice,
        LocalDateTime createdAt
) {}
