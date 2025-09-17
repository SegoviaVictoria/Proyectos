package com.civica.tiendaOnline.service;

import java.util.List;

import com.civica.tiendaOnline.dto.CategoryDTO;

public interface CategoryService {

    List<CategoryDTO> getAllCategoryDTOs();

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategoryDTOById(Long id);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategoryById(Long id);

}
