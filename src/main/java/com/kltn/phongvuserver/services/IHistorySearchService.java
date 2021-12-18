package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.HistorySearch;
import com.kltn.phongvuserver.models.recommendsystem.HotSearchDTO;

import java.util.List;
import java.util.Set;

public interface IHistorySearchService {
    List<HistorySearch> getAllHistorySearchByUser(int userId);
    void createOrUpdateHistorySearch(int userId, String keyword);
    void removeHistorySearch(int id);
    void removeAllHistorySearch(int userId);
    void autoHistorySearch();
    Set<String> recommendSearch(String keyword);
    List<HotSearchDTO> getTopSearchItem(int page, int size);
    List<String> getTopSearch(int page, int size);
}
