package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.dto.CountRatingProductDTO;
import com.kltn.phongvuserver.models.recommendsystem.AVGRatedProductDTO;
import com.kltn.phongvuserver.models.recommendsystem.RatingRSDTO;

import java.util.List;

public interface IRatingService {
    void autoRating();
    boolean checkExistId(int id);
    List<Rating> getAll();
    int checkUserIsRated(int uId);
    Rating save(Rating rating);
    CountRatingProductDTO countRatingByStarOfProduct(int productId);
    int countRatingByImageOfProduct(int productId);
    List<Rating> getRatingsByProductId(int productId, int page);
    List<Rating> getRatingByProductIdAndStar(int productId, int star, int page);
    List<Rating> getRatingByProductIdHasImage(int productId, int page);
    List<Rating> getRatingByUserAndStar(int userId, int star, int page, int pageSize);
    CountRatingProductDTO countStarRatingByUser(int id);
    int numberUserInRatings();
    int numberProductInRatings();
    List<Integer> getUsersRated();
    List<Integer> getProductsRated();
    List<AVGRatedProductDTO> calcAVGRatedProduct();
    List<Rating> getAllRatingByProductId(int productId, int page);
    void autoInsertRating();
    List<RatingRSDTO> getUserLeftJoinRating();
    Rating getRatingByProductAndProductOption(int userId, int productId, int productOptionId);
}
