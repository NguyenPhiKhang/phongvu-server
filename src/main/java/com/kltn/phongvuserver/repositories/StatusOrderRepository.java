package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.StatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusOrderRepository extends JpaRepository<StatusOrder, Integer> {
    @Query("select distinct so from StatusOrder so left join fetch so.orders o where o.user.id = :userId")
    List<StatusOrder> getListStatusOrderByUserForSummary(@Param("userId") int userId);
}
