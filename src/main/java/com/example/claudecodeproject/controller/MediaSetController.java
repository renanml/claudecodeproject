package com.example.claudecodeproject.controller;

import com.example.claudecodeproject.dto.MediaSetRequest;
import com.example.claudecodeproject.dto.MediaSetResponse;
import com.example.claudecodeproject.dto.PageResponse;
import com.example.claudecodeproject.service.MediaSetService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/media-sets")
public class MediaSetController {

    private final MediaSetService service;

    public MediaSetController(MediaSetService service) {
        this.service = service;
    }

    @GetMapping
    public PageResponse<MediaSetResponse> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.findAll(PageRequest.of(page - 1, size));
    }

    @GetMapping("/{id}")
    public MediaSetResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MediaSetResponse create(@Valid @RequestBody MediaSetRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public MediaSetResponse update(@PathVariable Long id, @Valid @RequestBody MediaSetRequest request) {
        return service.update(id, request);
    }
}
