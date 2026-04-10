package com.example.claudecodeproject.service;

import com.example.claudecodeproject.dto.MediaSetRequest;
import com.example.claudecodeproject.dto.MediaSetResponse;
import com.example.claudecodeproject.dto.PageResponse;
import com.example.claudecodeproject.model.MediaSet;
import com.example.claudecodeproject.repository.MediaSetRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MediaSetService {

    private final MediaSetRepository repository;

    public MediaSetService(MediaSetRepository repository) {
        this.repository = repository;
    }

    public PageResponse<MediaSetResponse> findAll(Pageable pageable) {
        return PageResponse.from(repository.findAll(pageable).map(MediaSetResponse::from));
    }

    public MediaSetResponse findById(Long id) {
        return repository.findById(id)
                .map(MediaSetResponse::from)
                .orElseThrow(() -> new RuntimeException("MediaSet not found"));
    }

    public MediaSetResponse create(MediaSetRequest request) {
        MediaSet mediaSet = new MediaSet();
        mediaSet.setThumbnail(request.thumbnail());
        mediaSet.setMedium(request.medium());
        mediaSet.setLarge(request.large());
        return MediaSetResponse.from(repository.save(mediaSet));
    }

    public MediaSetResponse update(Long id, MediaSetRequest request) {
        MediaSet mediaSet = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("MediaSet not found"));
        mediaSet.setThumbnail(request.thumbnail());
        mediaSet.setMedium(request.medium());
        mediaSet.setLarge(request.large());
        return MediaSetResponse.from(repository.save(mediaSet));
    }
}
