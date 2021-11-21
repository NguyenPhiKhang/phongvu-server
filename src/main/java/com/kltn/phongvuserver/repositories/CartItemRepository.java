package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer>, QuerydslPredicateExecutor<CartItem> {
    CartItem findCartItemByCartId(int cart_id);

    boolean existsCartItemById(int id);

    @Query(value = "SELECT ci.* FROM cart_item ci join carts c on ci.cart_id = c.id join users u on u.id = c.user_id where u.id =:user_id order by ci.updated_at desc", nativeQuery = true)
    List<CartItem> findCartItemByUserId(@Param("user_id") int userId);

    CartItem findCartItemByProductIdAndCartId(int product_id, int cart_id);
}
