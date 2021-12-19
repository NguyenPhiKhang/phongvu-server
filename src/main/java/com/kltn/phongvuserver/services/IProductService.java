package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.dto.InputReviewProductDTO;
import com.kltn.phongvuserver.models.dto.ProductDetailDTO;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import com.kltn.phongvuserver.models.dto.SearchProductDTO;

import java.util.HashMap;
import java.util.List;

public interface IProductService {
    List<ProductItemDTO> getProductsByCategory(int idCategory, int demand, int brand, int page, int pageSize);
    ProductDetailDTO getProductDetailById(int id, int userId);
    Product findProductByIdVisibleTrue(int id);
    List<Product> getAllProductVisibility();
    void reviewProduct(int userId, InputReviewProductDTO inputReview);
    List<Product> productTopRating(int page, int pageSize);
    List<Product> productRecommendForUser(int userId);
    List<HashMap<String, Double>> calcContentBasedTest(String textTest);
    List<SearchProductDTO> getProductSearch(String search, int page, int pageSize);
    List<ProductItemDTO> getProductsSimilarity(int id, int page, int pageSize);
    List<ProductItemDTO> getProductsAlsoLike(int userId, int page, int pageSize, String sameFor);
}
