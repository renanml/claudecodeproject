package com.example.claudecodeproject.dto;

import com.example.claudecodeproject.model.Category;

import java.time.LocalDateTime;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static CategoryResponse from(Category c) {
        return new CategoryResponse(c.getId(), c.getName(), c.getDescription(), c.getCreatedAt(), c.getModifiedAt());
    }
}
