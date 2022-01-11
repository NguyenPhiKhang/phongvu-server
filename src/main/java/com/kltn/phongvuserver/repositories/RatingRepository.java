package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.dto.CountRatingProductDTO;
import com.kltn.phongvuserver.models.recommendsystem.AVGRatedProductDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer>, QuerydslPredicateExecutor<Rating> {
    boolean existsByUserIdAndProductId(int user_id, int product_id);

    @Query(value = "select count(*) as col_0_0_ from ratings where product_id=:pId and star =:star", nativeQuery = true)
    int countStarInRating(@Param("pId") int pId, @Param("star")int star);

    @Query(value = "select count(*) as col_0_0_ from ratings where user_id=:uId", nativeQuery = true)
    int existsByUserId(@Param("uId") int userId);

    @Query(value = "select new com.kltn.phongvuserver.models.dto.CountRatingProductDTO(p.ratingStar.star1+p.ratingStar.star2+p.ratingStar.star3+p.ratingStar.star4+p.ratingStar.star5, p.ratingStar.star1, p.ratingStar.star2, p.ratingStar.star3, p.ratingStar.star4, p.ratingStar.star5) \n" +
            "from Product p \n" +
            "where p.id = :productId")
    CountRatingProductDTO countRatingByStar(@Param("productId") int productId);

    @Query(value = "select count(*) from ratings rr \n" +
            "where rr.id in (\n" +
            "select r.id\n" +
            "from ratings r \n" +
            "join rating_image ri \n" +
            "on r.id = ri.rating_id\n" +
            "where r.product_id = :productId\n" +
            "group by ri.rating_id);", nativeQuery = true)
    int countRatingByImage(@Param("productId") int productId);

    @Query(value = "SELECT * FROM ratings where product_id=:productId order by time_updated desc limit :p,20;", nativeQuery = true)
    List<Rating> findRatingsByProductId(@Param("productId") int productId, @Param("p") int page);

    @Query(value = "SELECT * FROM ratings where product_id=:productId and star=:star order by time_updated desc limit :p,20;", nativeQuery = true)
    List<Rating> findRatingsByProductIdAndStar(@Param("productId") int productId, @Param("star") int star, @Param("p") int page);

    @Query(value = "select r.*\n" +
            "from ratings r \n" +
            "join rating_image ri \n" +
            "on r.id = ri.rating_id \n" +
            "where r.product_id = :productId \n" +
            "group by ri.rating_id \n" +
            "order by r.time_updated desc limit :p,10;", nativeQuery = true)
    List<Rating> findRatingsByProductIdAndHasImage(@Param("productId") int productId, @Param("p") int page);

    @Query(value = "SELECT distinct r FROM Rating r join fetch r.product p left join fetch p.dataImages left join fetch r.dataImages " +
            "where r.user.id = :userId and r.star = case when :star=0 then r.star else :star end " +
            "order by r.timeUpdated desc")
    List<Rating> findRatingByUserAndStar(@Param("userId") int userId, @Param("star") int star, Pageable pageable);

    @Query(value = "SELECT star, count(*) as amount FROM phongvu_db.ratings where user_id = :userId group by star order by star asc", nativeQuery = true)
    List<Object[]> countStarRatingByUser(@Param("userId") int userId);

    List<Rating> findAllByOrderByProductAsc();

    @Query(value = "select distinct product_id from ratings order by product_id asc", nativeQuery = true)
    List<Integer> findProductsRated();

    @Query(value = "select distinct user_id from ratings order by user_id asc", nativeQuery = true)
    List<Integer> findUsersRated();

    @Query("select new com.kltn.phongvuserver.models.recommendsystem.AVGRatedProductDTO(u.product.id, avg(u.star)) from Rating u group by u.product order by u.product.id asc ")
    List<AVGRatedProductDTO> avgRatedProduct();
}
