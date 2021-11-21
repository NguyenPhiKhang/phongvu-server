package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Favorite;
import com.kltn.phongvuserver.models.embeddedID.ProductUserKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, ProductUserKey>, QuerydslPredicateExecutor<Favorite> {
    boolean existsFavoriteProductByIdProductIdAndIdUserId(int id_productId, int id_userId);
    Favorite findFavoriteProductByIdProductIdAndIdUserId(int id_productId, int id_userId);
    List<Favorite> getFavoriteByIdUserIdAndLikedTrueOrderByTimeUpdatedDesc(int id_userId);

    @Query("select f from Favorite f join fetch f.product p " +
            "join fetch p.category " +
            "join fetch p.ratingStar " +
            "left join fetch p.dataImages " +
            "where f.user.id = :userId and f.liked=true " +
            "order by f.timeUpdated desc")
    List<Favorite> getFavoriteByUserFetchForGetListProduct(@Param("userId") int userId, Pageable pageable);
    boolean existsFavoriteByIdProductIdAndIdUserIdAndLikedTrue(int id_productId, int id_userId);
}
