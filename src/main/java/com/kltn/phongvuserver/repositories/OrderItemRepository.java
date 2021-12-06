package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

//    @Query(value = "UPDATE `fashionshop_db`.`order_item` SET `review_status` = :isReview WHERE (`id` = :id);", nativeQuery = true)
//    void updateReviewStatusOrderItem(@Param("id") int id, @Param("isReview") int reviewStatus);

}
