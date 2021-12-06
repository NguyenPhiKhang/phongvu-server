package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Order;
import com.kltn.phongvuserver.models.dto.SummaryOrderDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, QuerydslPredicateExecutor<Order> {

    @Query("select distinct u from Order u left join fetch u.orderItems oi left join fetch oi.product " +
            "join fetch u.shipping " +
            "join fetch u.status stt " +
            "join fetch u.paymentMethod " +
            "where u.user.id=:user_id and stt.id= case when :status_id = 0 then stt.id else :status_id end " +
            "order by u.updatedAt desc")
    List<Order> findAllByUserIdAndStatusIdFetch(@Param("user_id") int user_id, @Param("status_id") int status_id, Pageable pageable);

    List<Order> findAllByUserIdAndStatusId(int user_id, int status_id);

    @Query("select u from Order u left join fetch u.orderItems oi join fetch oi.product " +
            "left join fetch u.shipping " +
            "order by u.updatedAt DESC ")
    List<Order> findAllOrderByUpdatedAtDescFetch(Pageable pageable);

    @Query("select o from Order o " +
            "join fetch o.shipping " +
            "join fetch o.paymentMethod " +
            "join fetch o.address a join fetch a.ward w join fetch w.district join fetch w.province " +
            "join fetch o.status " +
            "left join fetch o.orderItems oi join fetch oi.product " +
            "join fetch o.user " +
            "where o.id = :orderId")
    Order findOrderDetailById(@Param("orderId") int orderId);

    List<Order> findByOrderByUpdatedAtDesc(Pageable pageable);

    @Query(value = "select o.* from orders o join users u on o.user_id = u.id where u.name like %:search% and o.user_id = case when :userId = 0 then o.user_id else :userId end and o.status = case when :statusId = 0 then o.status else :statusId end order by o.status asc, o.created_at desc limit :page, :pageSize", nativeQuery = true)
    List<Order> findOrdersFilterForAdmin(@Param("userId") int userId, @Param("statusId") int statusId, @Param("search") String search, @Param("page") int page, @Param("pageSize") int pageSize);

    @Query(value = "select count(o.*) from orders o join users u on o.user_id = u.id where u.name like %:search% and o.user_id = case when :userId = 0 then o.user_id else :userId end and o.status = case when :statusId = 0 then o.status else :statusId end ", nativeQuery = true)
    int countOrdersFilterForAdmin(@Param("userId") int userId, @Param("statusId") int statusId, @Param("search") String search);

    @Query("select new com.kltn.phongvuserver.models.dto.SummaryOrderDTO(stt.id, stt.name, count(o.id)) from Order o " +
            "join o.status stt " +
            "where o.user.id=:userId " +
            "group by o.status")
    List<SummaryOrderDTO> getSummaryHistoryOrder(int userId);
}
