package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandRepository extends JpaRepository<Demand, Integer> {
    Demand findDemandById(Integer id);
}
