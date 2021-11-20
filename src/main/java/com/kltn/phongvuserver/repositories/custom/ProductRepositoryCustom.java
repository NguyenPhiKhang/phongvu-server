package com.kltn.phongvuserver.repositories.custom;

import com.kltn.phongvuserver.models.Product;

import java.util.List;
import java.util.Set;

public interface ProductRepositoryCustom {
    List<Product> findProductsItemByCategoryAndBrandOrDemand(Set<Integer> idCategories, int demand, int brand, int page);
}
