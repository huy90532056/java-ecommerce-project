package com.devteria.identityservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class TagCreationRequest {
    @NotBlank(message = "Tag name is required")
    private String tagName;
}
