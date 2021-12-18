package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.RecommendRating;
import com.kltn.phongvuserver.repositories.RecommendRatingRepository;
import com.kltn.phongvuserver.services.IRecommendRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendRatingService implements IRecommendRatingService {
    @Autowired
    private RecommendRatingRepository recommendRatingRepository;

    public boolean checkExistUser(int userId){
        return recommendRatingRepository.existsByUserId(userId);
    }

    public RecommendRating save(RecommendRating recommendRating){
        return recommendRatingRepository.save(recommendRating);
    }

    public RecommendRating getById(int user_id){
        return recommendRatingRepository.findByUserId(user_id);
    }

    public void removeAll(){
        recommendRatingRepository.deleteAll();
    }

    @Override
    public RecommendRating findRecommendRatingByUserId(int id) {
        return recommendRatingRepository.findByUserId(id);
    }
}
