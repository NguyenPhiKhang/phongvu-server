package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.RecommendRating;

public interface IRecommendRatingService {
    boolean checkExistUser(int userId);

    RecommendRating save(RecommendRating recommendRating);

    RecommendRating getById(int user_id);

    void removeAll();

    RecommendRating findRecommendRatingByUserId(int id);
}
