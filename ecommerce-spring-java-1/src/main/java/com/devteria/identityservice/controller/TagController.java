package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.TagCreationRequest;
import com.devteria.identityservice.dto.request.TagUpdateRequest;
import com.devteria.identityservice.dto.response.TagResponse;
import com.devteria.identityservice.service.TagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagController {
    TagService tagService;

    // Create a new Tag
    @PostMapping
    public ApiResponse<TagResponse> createTag(@RequestBody TagCreationRequest request) {
        TagResponse tagResponse = tagService.createTag(request);
        return ApiResponse.<TagResponse>builder()
                .result(tagResponse)
                .build();
    }

    // Get all Tags
    @GetMapping
    public ApiResponse<List<TagResponse>> getTags() {
        List<TagResponse> tags = tagService.getTags();
        return ApiResponse.<List<TagResponse>>builder()
                .result(tags)
                .build();
    }

    // Get Tag by ID
    @GetMapping("/{tagId}")
    public ApiResponse<TagResponse> getTag(@PathVariable String tagId) {
        TagResponse tagResponse = tagService.getTag(tagId);
        return ApiResponse.<TagResponse>builder()
                .result(tagResponse)
                .build();
    }

    // Update a Tag
    @PutMapping("/{tagId}")
    public ApiResponse<TagResponse> updateTag(@PathVariable String tagId, @RequestBody TagUpdateRequest request) {
        TagResponse tagResponse = tagService.updateTag(tagId, request);
        return ApiResponse.<TagResponse>builder()
                .result(tagResponse)
                .build();
    }

    // Delete a Tag
    @DeleteMapping("/{tagId}")
    public ApiResponse<String> deleteTag(@PathVariable String tagId) {
        tagService.deleteTag(tagId);
        return ApiResponse.<String>builder()
                .result("Tag has been deleted")
                .build();
    }
}
