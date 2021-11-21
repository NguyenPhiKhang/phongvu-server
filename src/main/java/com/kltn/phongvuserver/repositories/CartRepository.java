package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>, QuerydslPredicateExecutor<Cart> {
    Cart findCartByUserId(int user_id);
}
