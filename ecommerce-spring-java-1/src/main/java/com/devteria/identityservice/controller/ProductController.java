package com.devteria.identityservice.controller;

import com.devteria.identityservice.dto.request.ApiResponse;
import com.devteria.identityservice.dto.request.ProductCreationRequest;
import com.devteria.identityservice.dto.request.ProductUpdateRequest;
import com.devteria.identityservice.dto.response.ProductResponse;
import com.devteria.identityservice.entity.Product;
import com.devteria.identityservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping()
    ApiResponse<Product> createProduct(@RequestBody @Valid ProductCreationRequest request) {

        ApiResponse<Product> apiResponse = new ApiResponse<>();

        apiResponse.setResult(productService.createProduct(request));

        return apiResponse;
    }

    @GetMapping()
    List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    ProductResponse getProduct(@PathVariable("productId") Long productId) {
        return productService.getProduct(productId);
    }

    @PutMapping("{productId}")
    ProductResponse updateProduct(@PathVariable("productId") Long productId,
                                  @RequestBody ProductUpdateRequest request) {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    String deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return "User has been deleted";
    }

    @PutMapping("/{productId}/categories/{categoryId}")
    public ApiResponse<Product> addCategoryToProduct(@PathVariable("productId") Long productId,
                                                     @PathVariable("categoryId") String categoryId) {
        ApiResponse<Product> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.addCategoryToProduct(productId, categoryId));
        return apiResponse;
    }

    @PutMapping("/{productId}/tags/{tagId}")
    public ApiResponse<Product> addTagToProduct(@PathVariable("productId") Long productId,
                                                @PathVariable("tagId") String tagId) {
        ApiResponse<Product> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.addTagToProduct(productId, tagId));
        return apiResponse;
    }

}
