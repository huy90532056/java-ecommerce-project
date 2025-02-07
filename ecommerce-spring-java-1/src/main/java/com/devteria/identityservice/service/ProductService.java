package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.ProductCreationRequest;
import com.devteria.identityservice.dto.request.ProductUpdateRequest;
import com.devteria.identityservice.dto.response.ProductResponse;
import com.devteria.identityservice.entity.Category;
import com.devteria.identityservice.entity.Product;
import com.devteria.identityservice.entity.Tag;
import com.devteria.identityservice.mapper.ProductMapper;
import com.devteria.identityservice.repository.CategoryRepository;
import com.devteria.identityservice.repository.ProductRepository;
import com.devteria.identityservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;

    public Product createProduct(ProductCreationRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        return productRepository.save(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public ProductResponse getProduct(Long id) {
        return productMapper.toProductResponse(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found!")));
    }

    public ProductResponse updateProduct(Long productId, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        productMapper.updateProduct(product, productUpdateRequest);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    public Product addCategoryToProduct(Long productId, String categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category Not Found!"));

        product.getCategories().add(category);
        return productRepository.save(product);
    }

    public Product addTagToProduct(Long productId, String tagId) {
        // Tìm kiếm sản phẩm bằng productId
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        // Tìm kiếm tag bằng tagId
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag Not Found!"));

        // Thêm tag vào danh sách tags của sản phẩm
        product.getTags().add(tag);

        // Lưu lại sản phẩm sau khi thêm tag
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
