package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.repositories.custom.ProductRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, QuerydslPredicateExecutor<Product>, ProductRepositoryCustom {
    boolean existsProductByIdAndVisibilityTrue(int id);

    @Query(value = "select count(*) from demand_product where demand_id=:demandId and product_id=:productId", nativeQuery = true)
    int existsProductHaveDemand(@Param("productId") int productId, @Param("demandId") int demandId);

    List<Product> findProductsByVisibilityTrue();

    @Query(value = "select distinct p.* from products p, categories c, demand_product dp, brands b\n" +
            "where p.category_id = c.id and dp.product_id = p.id and p.brand_id = b.id\n" +
            "and p.category_id in (:idCategories)\n" +
            "and p.visibility = 1\n" +
            "and p.brand_id = case when :brand_id = 0 then p.brand_id else :brand_id end\n" +
            "and case when :demand_id = 0 then true else dp.demand_id in ( select t.demand_id from demand_product t where t.product_id = p.id and t.demand_id = :demand_id) end\n" +
            "limit :page, 20", nativeQuery = true)
    List<Product> findProductsByCategoryAndBrandOrDemand(@Param("idCategories") Set<Integer> idCategories,
                                                         @Param("demand_id") int demand,
                                                         @Param("brand_id") int brand,
                                                         @Param("page") int page);

    @Query(value = "select p from Product p where p.category.id in (:idCategories) " +
            "and p.brand.id = case when :brand_id=0 then p.brand.id else :brand_id end")
    List<Product> findProductsByCategoryAndBrand(@Param("idCategories") Set<Integer> idCategories,
                                                 @Param("brand_id") int brand,
                                                 Pageable pageable);
}
