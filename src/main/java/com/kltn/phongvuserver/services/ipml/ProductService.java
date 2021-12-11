package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.mappers.impl.ProductDetailDTOMapper;
import com.kltn.phongvuserver.mappers.impl.ProductItemDTOMapper;
import com.kltn.phongvuserver.models.Category;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.dto.ProductDetailDTO;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import com.kltn.phongvuserver.repositories.CategoryRepository;
import com.kltn.phongvuserver.repositories.ProductRepository;
import com.kltn.phongvuserver.services.IFavoriteService;
import com.kltn.phongvuserver.services.IProductService;
import com.kltn.phongvuserver.services.ISeenProductService;
import com.kltn.phongvuserver.utils.CategoryUtil;
import com.kltn.phongvuserver.utils.CommonUtil;
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

    @Autowired
    private ISeenProductService seenProductService;

    @Autowired
    private IFavoriteService favoriteService;

    @Autowired
    private ProductItemDTOMapper productItemDTOMapper;

    @Autowired
    private ProductDetailDTOMapper productDetailDTOMapper;

    @Override
    public List<ProductItemDTO> getProductsByCategory(int idCategory, int demand, int brand, int page) {
        Category category = categoryRepository.findCategoryFetchSubCategories(idCategory);
        Set<Integer> listId = new HashSet<>();

        Pageable pageable = CommonUtil.getPageForNativeQueryIsFalse(page, 20);
        int pageNew = CommonUtil.getPageForNativeQueryIsTrue(page, 20);

        List<Product> products;
        if (category.getName().contains("nhu cầu") || category.getName().contains("thương hiệu")) {
            listId.add(category.getParentCategory().getId());
            CategoryUtil.getListIdCategory(category.getParentCategory(), listId);

            products = productRepository.findProductsByCategoryAndBrandOrDemand(listId, demand, brand, pageNew);
        } else {
            listId.add(category.getId());
            CategoryUtil.getListIdCategory(category, listId);

            products = productRepository.findProductsByCategoryAndBrand(listId, brand, pageable);
        }

        return products.stream().map(p -> productItemDTOMapper.mapRow(p)).collect(Collectors.toList());
    }

    @Override
    public ProductDetailDTO getProductDetailById(int id, int userId) {
        Product product = productRepository.findProductDetailById(id);

        if (userId != 0) {
            seenProductService.createOrUpdateSeenProduct(userId, product);
            if (favoriteService.checkUserLikedProduct(userId, id)) {
                product.setLiked(true);
            }
        }

        return productDetailDTOMapper.mapRow(product);
    }

    @Override
    public Product findProductByIdVisibleTrue(int id) {
        return productRepository.findByIdAndVisibilityTrue(id);
    }
}
