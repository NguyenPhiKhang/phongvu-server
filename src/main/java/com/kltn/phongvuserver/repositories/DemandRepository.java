package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandRepository extends JpaRepository<Demand, Integer>, QuerydslPredicateExecutor<Demand> {
    Demand findDemandById(Integer id);
}
