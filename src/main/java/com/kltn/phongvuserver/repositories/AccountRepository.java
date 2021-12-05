package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Query(value = "select a from Account a join fetch a.permission join fetch a.user u join fetch u.imageAvatar left join fetch u.addresses " +
            "where (a.username = :username or u.email = :username) and a.password=:password")
    Account findByUsernameOrEmailAndPassword(@Param("username") String username, @Param("password") String password);

    @Query(value = "select count(*) from accounts a where a.user_id=:userId and a.password=:password", nativeQuery = true)
    int checkPasswordCorrect(@Param("userId") int userId, @Param("password") String password);

    @Transactional
    @Modifying
    @Query("update Account a set a.password=:new_password where a.user.id = :userId")
    void updatePassword(@Param("new_password") String password, @Param("userId") int userId);

    @Query(value = "select count(a) from Account a where a.username=:username or a.user.email=:username")
    int checkUsernameExisted(@Param("username") String usernameOrEmail);
}
