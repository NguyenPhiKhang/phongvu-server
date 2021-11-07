package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsProductByIdAndVisibilityTrue(int id);

    @Query(value = "select count(*) from demand_product where demand_id=:demandId and product_id=:productId", nativeQuery = true)
    int existsProductHaveDemand(@Param("productId") int productId, @Param("demandId") int demandId);
}
