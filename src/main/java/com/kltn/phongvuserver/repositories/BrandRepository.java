package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Brand findBrandByName(String name);

    @Query(value = "select distinct b.* from products p join brands b on p.brand_id = b.id where p.category_id=:categoryId ", nativeQuery = true)
    List<Brand> findListBrandByCategory(@Param("categoryId") int categoryId);
}
