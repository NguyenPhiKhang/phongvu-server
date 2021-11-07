package com.kltn.phongvuserver.controllers;

import com.kltn.phongvuserver.models.Category;
import com.kltn.phongvuserver.models.dto.CategoryDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface ICategoryController {
    @GetMapping("/categories/{parentId}/sub-categories")
    CategoryDTO getCategoriesByParentCategory(@PathVariable("parentId") int parentId);

    @GetMapping("/categories/get-categories-level1")
    List<Category> getCategoriesLevel1();

//    @GetMapping("/recommend-search")
//    ResponseEntity<Set<String>> getRecommendSearch(@RequestParam("keyword") String keyword);

    @PutMapping("/categories/create-path")
    String autoCreatePath();

    @PostMapping("/categories/auto-add-icon")
    String autoAddIconCategories(@RequestParam("id") int idCategory);

    @PostMapping("/categories/add-icon")
    String AddIconCategory();

    @GetMapping("/categories")
    List<Category> getCategoriesByLevel(@RequestParam("level") int level);

    @GetMapping("/category/{id}")
    Category getCategoryById(@PathVariable int id);

//    @GetMapping("/category/{id}/get-detail")
//    CategoryDetailDTO getPathCategory(@PathVariable int id);
//
//    @GetMapping("/categories/get-all")
//    List<CategoryScreenDTO> getAllCategories(@RequestParam(value = "p", defaultValue = "1") int page, @RequestParam(value = "p_size", defaultValue = "5") int pageSize, @RequestParam(value = "search", defaultValue = "") String search);
//
//    @GetMapping("/categories/count-record")
//    int countCategories(@RequestParam(value = "search", defaultValue = "") String search);
//
//    @RequestMapping(value = "/category/create-new", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    String createNewCategory(@ModelAttribute("input_category") InputCategoryDTO inputCategory);
//
//    @DeleteMapping("/category/{idCategory}/delete")
//    ResponseEntity<ResponseMessage<Integer>> deleteCategoryById(@PathVariable int idCategory);
}
