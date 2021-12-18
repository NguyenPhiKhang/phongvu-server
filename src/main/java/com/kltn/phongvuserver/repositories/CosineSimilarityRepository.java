package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.CosineSimilarity;
import com.kltn.phongvuserver.models.embeddedID.CosineSimilarityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CosineSimilarityRepository extends JpaRepository<CosineSimilarity, CosineSimilarityId> {
    CosineSimilarity findByColumnAndRow(int column, int row);
}
