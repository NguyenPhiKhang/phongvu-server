package com.kltn.phongvuserver.controllers;

import com.kltn.phongvuserver.models.RecommendRating;
import com.kltn.phongvuserver.models.dto.InputReviewProductDTO;
import com.kltn.phongvuserver.models.dto.ProductDetailDTO;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import com.kltn.phongvuserver.models.dto.SearchProductDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface IProductController {
    @GetMapping("/cat/{idCategory}/products")
    List<ProductItemDTO> getProductsByCategory(
            @PathVariable("idCategory") int idCategory,
            @RequestParam(value = "demand", defaultValue = "0") int demand,
            @RequestParam(value = "brand", defaultValue = "0") int brand,
            @RequestParam(value = "p", defaultValue = "1") int page,
            @RequestParam(value = "p_size", defaultValue = "20") int pageSize);


    @GetMapping("/product/{id}")
    ProductDetailDTO getProductById(@PathVariable int id, @RequestParam(value = "user", defaultValue = "0") int userId);

    @GetMapping("/recommend/top-rating/{userId}")
    ResponseEntity<List<ProductItemDTO>> getProductTopRating(@PathVariable("userId") int userId,
                                                             @RequestParam(value = "p", defaultValue = "1") int page,
                                                             @RequestParam(value = "p_size", defaultValue = "10") int pageSize);

    @PostMapping("/recommend/create-cosine-similarity")
    ResponseEntity<String> createCosineSimilarity();

    @PostMapping("/create-prediction-rating/{userId}")
    ResponseEntity<RecommendRating> recommend_product_for_user(@PathVariable("userId") int user_id);

//    @GetMapping("/test-recommend-movie")
//    ResponseEntity<List<RecommendForUser>> recommend_movie_test();

    @GetMapping("/calc-content-based-test")
    List<HashMap<String, Double>> calculationContentBasedTest(@RequestParam("s") String search);

    @GetMapping("/search-product")
    List<SearchProductDTO> searchProduct(@RequestParam("s") String search, @RequestParam(value = "p", defaultValue = "1") int page,
                                         @RequestParam(value = "p_size", defaultValue = "14") int pageSize);

    @GetMapping("/{userId}/search-product")
    List<SearchProductDTO> searchProduct(@PathVariable("userId") int userId, @RequestParam("s") String search, @RequestParam(value = "p", defaultValue = "1") int page,
                                         @RequestParam(value = "p_size", defaultValue = "14") int pageSize);

    @GetMapping("/product/{productId}/products-similarity")
    List<ProductItemDTO> productsSimilarity(@PathVariable("productId") int productId, @RequestParam(value = "p", defaultValue = "1") int page,
                                            @RequestParam(value = "p_size", defaultValue = "14") int pageSize);

    @GetMapping("/user/{userId}/products-also-like")
    List<ProductItemDTO> productsMightAlsoLike(@PathVariable int userId, @RequestParam(value = "p", defaultValue = "1") int page,
                                               @RequestParam(value = "p_size", defaultValue = "14") int pageSize);

}
