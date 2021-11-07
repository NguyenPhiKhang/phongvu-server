package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.ICategoryController;
import com.kltn.phongvuserver.models.Category;
import com.kltn.phongvuserver.models.dto.CategoryDTO;
import com.kltn.phongvuserver.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class CategoryController implements ICategoryController {
    @Autowired
    private ICategoryService categoryService;

    @Override
    public CategoryDTO getCategoriesByParentCategory(int parentId) {
        return categoryService.findCategoriesByParentId(parentId);
    }

    @Override
    public List<Category> getCategoriesLevel1() {
        return categoryService.findCategoriesLevel1();
    }

    @Override
    public String autoCreatePath() {
        return null;
    }

    @Override
    public String autoAddIconCategories(int idCategory) {
        return null;
    }

    @Override
    public String AddIconCategory() {
        return null;
    }

    @Override
    public List<Category> getCategoriesByLevel(int level) {
        return null;
    }

    @Override
    public Category getCategoryById(int id) {
        return null;
    }
}
