package com.kltn.phongvuserver.controllers;

import com.kltn.phongvuserver.models.HistorySearch;
import com.kltn.phongvuserver.models.recommendsystem.HotSearchDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface IHistorySearchController {
    @PostMapping("/{userId}/history-search")
    String createOrUpdateHistorySearch(@PathVariable("userId") int userId, @RequestParam(value = "keyword") String keyword);

    @GetMapping("/{userId}/get-history-search")
    List<HistorySearch> getListHistorySearchByUser(@PathVariable("userId") int userId);

    @DeleteMapping("/{userId}/remove-history-search")
    @Modifying
    @Transactional
    String removeHistorySearch(@PathVariable("userId") int userId, @RequestParam("hs") String hsType);

    @PostMapping("/history-search/auto")
    String autoHistorySearch();

    @GetMapping("/search/recommend-search")
    ResponseEntity<Set<String>> getRecommendSearch(@RequestParam("keyword") String keyword);

    @GetMapping("/search/hot-search-item")
    ResponseEntity<List<HotSearchDTO>> getHotSearchItem(@RequestParam(value = "p", defaultValue = "1") int page,
                                                        @RequestParam(value = "p_size", defaultValue = "4") int pageSize);

    @GetMapping("/search/hot-search-text")
    ResponseEntity<List<String>> getHotSearchText();
}
