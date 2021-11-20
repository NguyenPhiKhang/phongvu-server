package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer>, QuerydslPredicateExecutor<Rating> {
    boolean existsByUserIdAndProductId(int user_id, int product_id);
}
