package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.DataImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DataImageRepository extends JpaRepository<DataImage, String>, QuerydslPredicateExecutor<DataImage> {
    boolean existsDataImageById(String id);
}
