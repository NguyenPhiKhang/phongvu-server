package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.RatingStar;

public interface IRatingStarService {
    RatingStar getRatingStarById(int id);

    RatingStar save(RatingStar ratingStar);

    boolean existRatingStarId(int id);

    RatingStar getRatingStarByProductId(int productId);

    int totalStarsOfProduct(int productId);
}
