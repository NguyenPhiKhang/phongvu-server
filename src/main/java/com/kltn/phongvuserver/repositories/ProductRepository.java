package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.repositories.custom.ProductRepositoryCustom;
import org.springframework.data.domain.Page;
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

    List<Product> findProductsByRatingStarIsNull();

    Product findByIdAndVisibilityTrue(int id);

    @Query(value = "select count(*) from demand_product where demand_id=:demandId and product_id=:productId", nativeQuery = true)
    int existsProductHaveDemand(@Param("productId") int productId, @Param("demandId") int demandId);

    List<Product> findProductsByVisibilityTrue();

    @Query(value = "select p from Product p left join fetch p.ratingStar left join fetch p.dataImages" +
            " where p.visibility = true")
    List<Product> findProductsForSearch();

    @Query("select p from Product p where p.visibility = true")
    List<Product> getProductsVisibilityTrue();

    @Query(value = "select distinct p.* from products p, categories c, demand_product dp, brands b, image_product ip, data_images di\n" +
            "where p.category_id = c.id and dp.product_id = p.id and p.brand_id = b.id and p.id = ip.product_id and di.id = ip.image_id\n" +
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

    @Query("select p from Product p join fetch p.category join fetch p.dataImages where p.id=:id")
    Product findProductByIdFetchCategoryAndDataImage(@Param("id") int id);

    @Query("select p from Product p " +
            "join fetch p.category " +
            "left join fetch p.dataImages " +
            "left join fetch p.brand " +
            "left join fetch p.ratingStar " +
            "left join fetch p.productAttributes pa join fetch pa.attribute " +
            "where p.id = :id and p.visibility=true")
    Product findProductDetailById(@Param("id") int id);

    // Calculate mean of vote average column
    @Query(value = "SELECT avg(case when (star1+star2+star3+star4+star5)>0 then (star1 + star2*2 + star3*3 + star4*4 + star5*5)/(star1+star2+star3+star4+star5) else 0 end) FROM rating_star;", nativeQuery = true)
    float meanOfVoteAverage();

    @Query(value = "call calculate_quantile();", nativeQuery = true)
    float calculateQuantile();

    @Query(value = "select p.* from rating_star as r join products as p on r.id = p.rating_star_id where (star1+star2+star3+star4+star5)>=:m order by ((((star1+star2+star3+star4+star5)/((star1+star2+star3+star4+star5)+:m))*(case when (star1+star2+star3+star4+star5)>0 then (star1 + star2*2 + star3*3 + star4*4 + star5*5)/(star1+star2+star3+star4+star5) else 0 end)) + ((:m/(:m+(star1+star2+star3+star4+star5)))*:C)) desc limit :page, :pageSize", nativeQuery = true)
    List<Product> topRatingProducts(@Param("m") float m, @Param("C") float C, @Param("page") int page, @Param("pageSize") int pageSize);

    @Query("select u from Product u where u.id in (:products)")
    List<Product> findProductByListIdProduct(@Param("products") List<Integer> products);

    @Query(value = "call get_shortdesc_name(:productId)", nativeQuery = true)
    String getShortDescriptionOrName(@Param("productId") int productId);

    @Query(value = "Select c.name from products p join categories c on p.category_id = c.id where p.id=:id", nativeQuery = true)
    String findNameCategoryByProduct(@Param("id") int id);

    @Query("select p from Product p where p.visibility = true and p.id <> :productId")
    List<Product> getProductAndShortDescriptionExceptProduct(@Param("productId") int productId);

    @Query(value = "Select case when (p.description <> '' and p.description is not null and p.description not like '%đang cập nhật%' and p.description not like '%Mô tả sản phẩm sẽ được cập nhật trong thời gian sớm nhất%') " +
            "then p.description else case when (p.short_description <> '' and p.short_description is not null) " +
            "then p.short_description else " +
            "p.name end end as txt_description, s.count as count_seen, s.product_id as product_id from `phongvu_db`.`products` as p inner join `phongvu_db`.`seen_products` as s on p.id = s.product_id where s.user_id = :userId and CURRENT_TIMESTAMP - s.last_time < 7000000 order by s.last_time desc", nativeQuery = true)
    Page<Object[]> getShortDescriptionOrNameByUser(@Param("userId") int userId, Pageable pageable);

    @Query("select p from Product p where p.visibility = true and p.id not in :listIdProduct")
    List<Product> getProductAndShortDescriptionExceptListProduct(@Param("listIdProduct") List<Integer> listIdProduct);
}
