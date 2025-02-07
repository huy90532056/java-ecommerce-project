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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    // Tạo mới Product
    @CacheEvict(value = "products", allEntries = true) // Xóa cache của danh sách sản phẩm khi tạo mới
    public Product createProduct(ProductCreationRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        return productRepository.save(product);
    }

    // Lấy danh sách tất cả sản phẩm
    @Cacheable(value = "products") // Cache toàn bộ danh sách sản phẩm
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    // Lấy thông tin chi tiết của một sản phẩm
    @Cacheable(value = "product", key = "#id") // Cache thông tin sản phẩm theo ID
    public ProductResponse getProduct(Long id) {
        return productMapper.toProductResponse(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found!")));
    }

    // Cập nhật thông tin sản phẩm
    @CachePut(value = "product", key = "#productId") // Cập nhật lại cache cho sản phẩm đã được chỉnh sửa
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        productMapper.updateProduct(product, productUpdateRequest);

        return productMapper.toProductResponse(productRepository.save(product));
    }

    // Thêm category vào Product
    @CacheEvict(value = "product", key = "#productId") // Xóa cache cũ vì product đã thay đổi
    public Product addCategoryToProduct(Long productId, String categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category Not Found!"));

        product.getCategories().add(category);
        return productRepository.save(product);
    }

    // Thêm tag vào Product
    @CacheEvict(value = "product", key = "#productId") // Xóa cache cũ vì product đã thay đổi
    public Product addTagToProduct(Long productId, String tagId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found!"));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag Not Found!"));

        product.getTags().add(tag);

        return productRepository.save(product);
    }

    // Xóa Product
    @CacheEvict(value = {"product", "products"}, key = "#productId", allEntries = true) // Xóa cache liên quan
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}