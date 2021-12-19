package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IHistorySearchController;
import com.kltn.phongvuserver.models.HistorySearch;
import com.kltn.phongvuserver.models.recommendsystem.HotSearchDTO;
import com.kltn.phongvuserver.services.IHistorySearchService;
import com.kltn.phongvuserver.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class HistorySearchController implements IHistorySearchController {
    @Autowired
    private IHistorySearchService historySearchService;

    @Override
    public String createOrUpdateHistorySearch(int userId, String keyword) {
        historySearchService.createOrUpdateHistorySearch(userId, keyword);

        return "Thành công";
    }

    @Override
    public List<HistorySearch> getListHistorySearchByUser(int userId) {
        return historySearchService.getAllHistorySearchByUser(userId);
    }

    @Override
    public String removeHistorySearch(int userId, String hsType) {
        try {
            if (hsType.equals("all"))
                historySearchService.removeAllHistorySearch(userId);
            else {
                if (StringUtil.checkStringIsNumeric(hsType))
                    historySearchService.removeHistorySearch(Integer.parseInt(hsType));
                else return "Xoá không thành công.";
            }
            return "Xoá thành công";
        }catch (Exception exception){
            return "Xoá không thành công.";
        }
    }

    @Override
    public String autoHistorySearch() {
        historySearchService.autoHistorySearch();
        return "Done";
    }

    @Override
    public ResponseEntity<Set<String>> getRecommendSearch(String keyword) {
        return ResponseEntity.ok().body(historySearchService.recommendSearch(keyword));
    }

    @Override
    public ResponseEntity<List<HotSearchDTO>> getHotSearchItem(int page, int pageSize) {
        return ResponseEntity.ok().body(historySearchService.getTopSearchItem(page, pageSize));
    }

    @Override
    public ResponseEntity<List<String>> getHotSearchText() {
        return ResponseEntity.ok().body(historySearchService.getTopSearch(1,12));
    }
}
