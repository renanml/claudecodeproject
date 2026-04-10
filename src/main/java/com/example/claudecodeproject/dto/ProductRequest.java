package com.example.claudecodeproject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotBlank String name,
        String description,
        Long categoryId,
        @NotNull @Valid PriceRequest price,
        @Valid MediaSetRequest mediaSet
) {}
