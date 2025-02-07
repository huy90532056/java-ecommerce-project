package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.TagCreationRequest;
import com.devteria.identityservice.dto.request.TagUpdateRequest;
import com.devteria.identityservice.dto.response.TagResponse;
import com.devteria.identityservice.entity.Tag;
import com.devteria.identityservice.exception.AppException;
import com.devteria.identityservice.exception.ErrorCode;
import com.devteria.identityservice.repository.TagRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagService {
    TagRepository tagRepository;

    // Create new Tag
    public TagResponse createTag(TagCreationRequest request) {
        if (tagRepository.existsByTagName(request.getTagName())) {
            throw new AppException(ErrorCode.TAG_EXISTED);
        }

        Tag tag = new Tag();
        tag.setTagName(request.getTagName());

        // Save tag to repository
        tag = tagRepository.save(tag);

        // Convert Tag entity to TagResponse
        TagResponse response = new TagResponse();
        response.setTagId(tag.getTagId());
        response.setTagName(tag.getTagName());

        return response;
    }

    // Get all Tags
    public List<TagResponse> getTags() {
        return tagRepository.findAll().stream()
                .map(tag -> {
                    TagResponse response = new TagResponse();
                    response.setTagId(tag.getTagId());
                    response.setTagName(tag.getTagName());
                    return response;
                })
                .toList();
    }

    // Get Tag by ID
    public TagResponse getTag(String tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_EXISTED));

        // Convert Tag entity to TagResponse
        TagResponse response = new TagResponse();
        response.setTagId(tag.getTagId());
        response.setTagName(tag.getTagName());

        return response;
    }

    // Update Tag
    public TagResponse updateTag(String tagId, TagUpdateRequest request) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_EXISTED));

        tag.setTagName(request.getTagName());

        tag = tagRepository.save(tag);

        // Convert updated Tag entity to TagResponse
        TagResponse response = new TagResponse();
        response.setTagId(tag.getTagId());
        response.setTagName(tag.getTagName());

        return response;
    }

    // Delete Tag
    public void deleteTag(String tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new AppException(ErrorCode.TAG_NOT_EXISTED);
        }

        tagRepository.deleteById(tagId);
    }
}
