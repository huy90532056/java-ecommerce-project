package com.devteria.identityservice.service;

import com.devteria.identityservice.dto.request.CategoryDto;
import com.devteria.identityservice.entity.Category;
import com.devteria.identityservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryIcon(categoryDto.getCategoryIcon());
        category.setCategoryImagePath(categoryDto.getCategoryImagePath());
        category.setActive(categoryDto.getActive() != null ? categoryDto.getActive() : true);
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category Not Found!"));
    }

    public Category updateCategory(String id, CategoryDto categoryDto) {
        Category category = getCategoryById(id);
        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryIcon(categoryDto.getCategoryIcon());
        category.setCategoryImagePath(categoryDto.getCategoryImagePath());
        category.setActive(categoryDto.getActive());
        return categoryRepository.save(category);
    }

    public void deleteCategory(String id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }
}
