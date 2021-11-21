package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.Brand;
import com.kltn.phongvuserver.models.Category;
import com.kltn.phongvuserver.models.dto.CategoryDTO;
import com.kltn.phongvuserver.repositories.BrandRepository;
import com.kltn.phongvuserver.repositories.CategoryRepository;
import com.kltn.phongvuserver.services.ICategoryService;
import com.kltn.phongvuserver.utils.EnvUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private EnvUtil envUtil;

    @SneakyThrows
    @Override
    public CategoryDTO findCategoriesByParentId(int id) {
        Category category = categoryRepository.findCategoryByIdFetchCategorySubLevel1(id);
        if (category != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setIcon(category.getIcon());
            categoryDTO.setPath(
                    envUtil.getServerUrlPrefi()
                            .concat("/api/v1/cat/")
                            .concat(String.valueOf(category.getId()))
                            .concat("/products"));

            categoryDTO.setSubCategories(category.getCategories().stream()
                    .sorted(Comparator.comparingInt(Category::getId))
                    .map(subCategory -> {
                        CategoryDTO categoryDTOSub = new CategoryDTO();
                        categoryDTOSub.setId(subCategory.getId());
                        categoryDTOSub.setName(subCategory.getName());
                        categoryDTOSub.setIcon(subCategory.getIcon());
                        try {
                            categoryDTOSub.setPath(envUtil.getServerUrlPrefi()
                                    .concat("/api/v1/cat/")
                                    .concat(String.valueOf(subCategory.getId()))
                                    .concat("/products"));
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }
                        if (subCategory.getName().contains("nhu cầu")) {
                            categoryDTOSub.setSubCategories(
                                    subCategory.getDemands().stream().map(demand -> {
                                        CategoryDTO categoryDTODemand = new CategoryDTO();
                                        categoryDTODemand.setName(demand.getName());
                                        categoryDTODemand.setIcon(demand.getIcon());
                                        try {
                                            categoryDTODemand.setPath(envUtil.getServerUrlPrefi()
                                                    .concat("/api/v1/cat/")
                                                    .concat(String.valueOf(subCategory.getId()))
                                                    .concat("/products")
                                                    .concat("?demand=")
                                                    .concat(String.valueOf(demand.getId())));
                                        } catch (UnknownHostException e) {
                                            e.printStackTrace();
                                        }
                                        return categoryDTODemand;
                                    }).collect(Collectors.toList()));
                        } else {
                            List<Brand> brands = brandRepository.findListBrandByCategory(subCategory.getName().contains("thương hiệu") ? category.getId() : subCategory.getId());
                            categoryDTOSub.setSubCategories(brands.stream().map(brand -> {
                                CategoryDTO categoryDTOBrand = new CategoryDTO();
                                categoryDTOBrand.setName(brand.getName());
                                categoryDTOBrand.setIcon(brand.getIcon());
                                try {
                                    categoryDTOBrand.setPath(envUtil.getServerUrlPrefi()
                                            .concat("/api/v1/cat/")
                                            .concat(String.valueOf(subCategory.getId()))
                                            .concat("/products")
                                            .concat("?brand=")
                                            .concat(String.valueOf(brand.getId())));
                                } catch (UnknownHostException e) {
                                    e.printStackTrace();
                                }
                                return categoryDTOBrand;
                            }).collect(Collectors.toList()));
                        }

                        return categoryDTOSub;
                    }).collect(Collectors.toList()));

            return categoryDTO;
        } else return null;
    }

    @Override
    public List<Category> findCategoriesLevel1() {
        return categoryRepository.findCategoriesByLevel(1);
    }

    @Override
    public List<String> getAllNameCategories() {
        return null;
    }

    @Override
    public String autoSetIconCategory(int idCategory) {
        return null;
    }

    @Override
    public Category findCategoryById(int id) {
        return null;
    }

    @Override
    public String addIconCategories(String[] arrIcon) {
        return null;
    }

    @Override
    public List<Category> findAllCategories() {
        return null;
    }

    @Override
    public List<Category> findAllCategoriesOrderByLevel(int page, int pageSize, String search) {
        return null;
    }

    @Override
    public int countCategories(String search) {
        return 0;
    }

    @Override
    public List<Category> findCategoryByLevel(int level) {
        return null;
    }

    @Override
    public void saveAll(List<Category> categories) {

    }

    @Override
    public String getPathCategory(int id) {
        return null;
    }
}
