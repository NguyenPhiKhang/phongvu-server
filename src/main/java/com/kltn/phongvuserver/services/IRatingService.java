package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.dto.CountRatingProductDTO;

import java.util.List;

public interface IRatingService {
    void autoRating();
    CountRatingProductDTO countRatingByStarOfProduct(int productId);
    int countRatingByImageOfProduct(int productId);
    List<Rating> getAllRatingByProductId(int productId, int page);
    List<Rating> getRatingByProductIdAndStar(int productId, int star, int page);
    List<Rating> getRatingByProductIdHasImage(int productId, int page);
}
