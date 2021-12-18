package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.RecommendRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendRatingRepository extends JpaRepository<RecommendRating, Integer> {
    RecommendRating findByUserId(int userId);
    boolean existsByUserId(int userId);

    @Modifying
    @Query(value = "delete from phongvu_db.recommend_rating", nativeQuery = true)
    void deleteAllRecommend();
}
