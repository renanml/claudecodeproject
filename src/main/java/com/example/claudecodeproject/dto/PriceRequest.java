package com.example.claudecodeproject.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PriceRequest(
        @NotNull @DecimalMin("0.00") BigDecimal salePrice,
        @NotNull @DecimalMin("0.00") BigDecimal listPrice
) {}
