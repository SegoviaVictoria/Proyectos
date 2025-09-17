package com.civica.tiendaOnline.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.civica.tiendaOnline.dto.CategoryDTO;
import com.civica.tiendaOnline.model.Category;
import com.civica.tiendaOnline.model.Product;
import com.civica.tiendaOnline.repository.CategoryRepository;
import com.civica.tiendaOnline.repository.ProductRepository;
import com.civica.tiendaOnline.service.CategoryService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CategoryDTO> getAllCategoryDTOs() {
        return categoryRepository.findAll().stream()
                .map(this::toCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryDTOById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with ID " + id + " not found"));
        return toCategoryDTO(category);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();

        validateCategoryDTO(categoryDTO);
        category.setName(categoryDTO.getName());
        createListProductsIfNotNull(categoryDTO, category);
        categoryRepository.save(category);

        return toCategoryDTO(category);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with ID " + id + " not found"));

        validateCategoryDTOForUpdate(id, categoryDTO);
        existingCategory.setName(categoryDTO.getName());
        createListProductsIfNotNull(categoryDTO, existingCategory);
        categoryRepository.save(existingCategory);

        return toCategoryDTO(existingCategory);

    }

    @Override
    public void deleteCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with ID " + id + " not found"));

        if (!category.getProducts().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category has associated products");
        }

        categoryRepository.deleteById(id);
    }

    // ----------------------- EXTRA METHODS ----------------------- //
    private void validateCategoryDTO(CategoryDTO categoryDTO) {

        if (categoryDTO == null) {
            throw new IllegalArgumentException("CategoryDTO must not be null");
        }

        String categoryDTOName = categoryDTO.getName();

        if (categoryDTOName == null || categoryDTOName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name must not be null or empty");
        }

        Optional<Category> existingCategory = categoryRepository.findByName(categoryDTOName);
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("Category with name '" + categoryDTOName + "' already exists");
        }
    }

    private void validateCategoryDTOForUpdate(Long id, CategoryDTO categoryDTO) {

        if (categoryDTO == null) {
            throw new IllegalArgumentException("CategoryDTO must not be null");
        }

        String categoryDTOName = categoryDTO.getName();

        if (categoryDTOName == null || categoryDTOName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name must not be null or empty");
        }

        Optional<Category> categoryWithSameName = categoryRepository.findByName(categoryDTOName);
        if (categoryWithSameName.isPresent() && !categoryWithSameName.get().getId().equals(id)) {
            throw new IllegalArgumentException("Category with name '" + categoryDTOName + "' already exists");
        }
    }

    private void createListProductsIfNotNull(CategoryDTO categoryDTO, Category category) {

        if (categoryDTO.getProductNames() != null) {
            List<Product> products = productRepository.findAll().stream()
                    .filter(p -> categoryDTO.getProductNames().contains(p.getName()))
                    .collect(Collectors.toList());
            category.setProducts(products);
        }

    }

    public CategoryDTO toCategoryDTO(Category category) {
        List<String> productNames = category.getProducts()
                .stream()
                .map(product -> product.getName())
                .collect(Collectors.toList());
        return new CategoryDTO(category.getId(), category.getName(), productNames);
    }
}
