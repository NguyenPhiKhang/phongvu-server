package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.dto.ProductDetailDTO;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;

import java.util.List;

public interface IProductService {
    List<ProductItemDTO> getProductsByCategory(int idCategory, int demand, int brand, int page);
    ProductDetailDTO getProductDetailById(int id, int userId);
    Product findProductByIdVisibleTrue(int id);
}
