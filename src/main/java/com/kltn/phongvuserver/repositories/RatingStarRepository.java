package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.RatingStar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingStarRepository extends JpaRepository<RatingStar, Integer>, QuerydslPredicateExecutor<RatingStar> {
}
