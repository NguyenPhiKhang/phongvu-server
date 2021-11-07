package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Category;
import com.kltn.phongvuserver.models.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    CategoryDTO findCategoriesByParentId(int id);
    List<Category> findCategoriesLevel1();
    List<String> getAllNameCategories();
    String autoSetIconCategory(int idCategory);
    Category findCategoryById(int id);
    String addIconCategories(String[] arrIcon);
    List<Category> findAllCategories();
    List<Category> findAllCategoriesOrderByLevel(int page, int pageSize, String search);
    int countCategories(String search);
    List<Category> findCategoryByLevel(int level);
    void saveAll(List<Category> categories);
    String getPathCategory(int id);
    //    Set<String> recommendSearch(String keyword);
//    void createNewCategory(InputCategoryDTO inputCategory);
}
