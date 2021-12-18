package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.RecommendRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendRatingRepository extends JpaRepository<RecommendRating, Integer> {
    RecommendRating findByUserId(int userId);
    boolean existsByUserId(int userId);
}
