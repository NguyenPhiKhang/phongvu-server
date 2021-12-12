package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.dto.CountRatingProductDTO;

import java.util.List;

public interface IRatingService {
    void autoRating();
    boolean checkExistId(int id);
    Rating save(Rating rating);
    CountRatingProductDTO countRatingByStarOfProduct(int productId);
    int countRatingByImageOfProduct(int productId);
    List<Rating> getRatingsByProductId(int productId, int page);
    List<Rating> getRatingByProductIdAndStar(int productId, int star, int page);
    List<Rating> getRatingByProductIdHasImage(int productId, int page);
    List<Rating> getRatingByUserAndStar(int userId, int star, int page, int pageSize);
    CountRatingProductDTO countStarRatingByUser(int id);
}
