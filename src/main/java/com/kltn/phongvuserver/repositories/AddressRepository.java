package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Modifying
    @Transactional
    @Query(value = "update addresses a set a.default_is = 0 where a.user_id = :userId", nativeQuery = true)
    void setDefaultIsFalse(@Param("userId") int userId);

    @Query(value = "select a from Address a join fetch a.ward w join fetch w.district join fetch w.province " +
            "where a.user.id = :user_id order by a.updateAt desc")
    List<Address> findAddressByUserIdOrderByUpdateAtFetchDesc(@Param("user_id") int user_id);
}
