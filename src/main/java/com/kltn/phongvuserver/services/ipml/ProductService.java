package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.mappers.impl.ProductItemDTOMapper;
import com.kltn.phongvuserver.models.Category;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import com.kltn.phongvuserver.repositories.CategoryRepository;
import com.kltn.phongvuserver.repositories.ProductRepository;
import com.kltn.phongvuserver.services.IProductService;
import com.kltn.phongvuserver.utils.CategoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ProductService implements IProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductItemDTO> getProductsByCategory(int idCategory, int demand, int brand, int page) {
        Category category = categoryRepository.findCategoryFetchSubCategories(idCategory);
        Set<Integer> listId = new HashSet<>();

        Pageable pageable = PageRequest.of(page - 1, 20);
        int pageNew = page < 1 ? 0 : (page - 1) * 20;

        List<ProductItemDTO> listProduct = new ArrayList<>();
        List<Product> products;

        if (category.getName().contains("nhu cầu") || category.getName().contains("thương hiệu")) {
            listId.add(category.getParentCategory().getId());
            CategoryUtil.getListIdCategory(category.getParentCategory(), listId);
            List<Product> productTest = productRepository.findProductsItemByCategoryAndBrandOrDemand(listId, demand, brand, pageNew);

            products = productRepository.findProductsByCategoryAndBrandOrDemand(listId, demand, brand, pageNew);
        } else {
            CategoryUtil.getListIdCategory(category.getParentCategory(), listId);
            products =productRepository.findProductsByCategoryAndBrand(listId, brand, pageable);
        }

        listProduct.addAll(products.stream().map(p-> new ProductItemDTOMapper().mapRow(p)).collect(Collectors.toList()));
        return listProduct;
    }
}
