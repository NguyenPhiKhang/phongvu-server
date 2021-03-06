package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.SeenProduct;
import com.kltn.phongvuserver.models.embeddedID.ProductUserKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeenProductRepository extends JpaRepository<SeenProduct, ProductUserKey>, QuerydslPredicateExecutor<SeenProduct> {

//    @Query(value = "select count(*) from seen_products where product_id = :productId and user_id=:user_id", nativeQuery = true)
//    int existsSeenProductByProductIdAndUserId();

    boolean existsSeenProductByIdProductIdAndIdUserId(int id_productId, int id_userId);
    SeenProduct findSeenProductByIdProductIdAndIdUserId(int id_productId, int id_userId);

    @Query(value = "SELECT * FROM phongvu_db.seen_products where user_id = :id_userId and CURRENT_TIMESTAMP - last_time < 7000000 order by last_time desc limit 10", nativeQuery = true)
    List<SeenProduct> findSeenProductByUserAndLastWeek(@Param("id_userId") int id_userId);

    @Query(value = "select sp from SeenProduct sp join fetch sp.product p join fetch p.ratingStar left join fetch p.dataImages " +
            "where sp.user.id = :user_id order by sp.lastTime desc ")
    List<SeenProduct> findSeenProductByUserIdOrderByLastTimeDesc(int user_id, Pageable pageable);
}
