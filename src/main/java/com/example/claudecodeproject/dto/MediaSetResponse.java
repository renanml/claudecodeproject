package com.example.claudecodeproject.dto;

import com.example.claudecodeproject.model.MediaSet;

import java.time.LocalDateTime;

public record MediaSetResponse(
        Long id,
        String thumbnail,
        String medium,
        String large,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static MediaSetResponse from(MediaSet m) {
        return new MediaSetResponse(m.getId(), m.getThumbnail(), m.getMedium(), m.getLarge(), m.getCreatedAt(), m.getModifiedAt());
    }
}
