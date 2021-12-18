package com.kltn.phongvuserver.repositories;

import com.kltn.phongvuserver.models.HistorySearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@Repository
public interface HistorySearchRepository extends JpaRepository<HistorySearch, Integer> {
    //    @Query("select hs from HistorySearch hs where hs.user.id = :userId order by hs.timeSearch desc")
    List<HistorySearch> getHistorySearchByUserIdOrderByTimeSearchDesc(int user_id);

    boolean existsHistorySearchByUserIdAndKeyword(int user_id, String keyword);

    HistorySearch findHistorySearchByUserIdAndKeyword(int user_id, String keyword);

    @Query(value = "select * from history_search hs where hs.user_id = :userId order by hs.id desc limit 1", nativeQuery = true)
    HistorySearch findTop1HistorySearchByUserId(@Param("userId") int userId);

    @Query(value = "delete from history_search where user_id = :user_id", nativeQuery = true)
    void deleteHistorySearchesByUserId(@Param("user_id") int user_id);

    void deleteAllByUserId(int user_id);

    @Query(value = "select keyword from fashionshop_db.history_search group by keyword order by count(keyword) desc", nativeQuery = true)
    List<String> getAllSearch();

    @Query(value = "select keyword from fashionshop_db.history_search group by keyword order by count(keyword) desc", nativeQuery = true)
    Page<String> getTopSearch(Pageable pageable);

    @Query(value = "SELECT keyword FROM fashionshop_db.history_search where keyword like :txt_search% group by keyword order by count(keyword) desc limit 15", nativeQuery = true)
    Set<String> getTopSearchLike(@Param("txt_search") String keyword);
}
