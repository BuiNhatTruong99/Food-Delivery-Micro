package com.food_delivery.restaurant.service;

import com.food_delivery.restaurant.dto.request.category.CategoryCreateRequest;
import com.food_delivery.restaurant.dto.request.category.CategoryUpdateRequest;
import com.food_delivery.restaurant.dto.response.CategoryResponse;
import com.food_delivery.restaurant.entity.Category;
import com.food_delivery.restaurant.exception.DuplicateResourceException;
import com.food_delivery.restaurant.exception.ErrorCode;
import com.food_delivery.restaurant.exception.ResourceNotFoundException;
import com.food_delivery.restaurant.mapper.CategoryMapper;
import com.food_delivery.restaurant.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        log.info("CATEGORY: START CREATING");
        Optional<Category> categoryOptional =
                categoryRepository.findByName(request.getName());
        if (categoryOptional.isPresent()) {
            log.error("CATEGORY: {} ALREADY EXISTS", request.getName());
            throw new DuplicateResourceException(ErrorCode.ERR_CATEGORY_DUPLICATE);
        }
        var category = categoryMapper.toCategory(request);
        categoryRepository.save(category);
        log.info("CATEGORY: CREATED");
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(CategoryUpdateRequest request) {
        log.info("CATEGORY: START UPDATING");
        var category = categoryRepository
                .findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_CATEGORY_NOT_FOUND));
        category.setName(request.getName());
        category.setImageUrl(request.getImageUrl());
        categoryRepository.save(category);
        log.info("CATEGORY: UPDATED");
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getCategories() {
        var categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Category> getCategoriesByIds(List<String> categoryIds) {
        return categoryIds.stream()
                .map(id -> categoryRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                ErrorCode.ERR_CATEGORY_NOT_FOUND)
                        )).toList();
    }

    @Override
    public void deleteCategory(String id) {
        log.info("CATEGORY: START DELETING");
        var category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorCode.ERR_CATEGORY_NOT_FOUND));
        categoryRepository.delete(category);
        log.info("CATEGORY: DELETED");
    }
}
