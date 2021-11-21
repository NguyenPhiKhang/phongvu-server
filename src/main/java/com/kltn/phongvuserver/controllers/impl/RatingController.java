package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IRatingController;
import com.kltn.phongvuserver.mappers.impl.RatingDTOMapper;
import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.dto.CountRatingProductDTO;
import com.kltn.phongvuserver.models.dto.RatingProductDTO;
import com.kltn.phongvuserver.services.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Transactional(rollbackFor = Throwable.class)
public class RatingController implements IRatingController {
    @Autowired
    private IRatingService ratingService;

    @Override
    public ResponseEntity<String> autoRating() {
        ratingService.autoRating();
        return ResponseEntity.ok().body("done");
    }

    @Override
    public ResponseEntity<RatingProductDTO> getRatingByProduct(int id, String select, int page) {
        RatingProductDTO ratingProductDTO = new RatingProductDTO();

        CountRatingProductDTO countRatingProductDTO = ratingService.countRatingByStarOfProduct(id);
        countRatingProductDTO.setTotalImage(ratingService.countRatingByImageOfProduct(id));

        ratingProductDTO.setTotalCount(countRatingProductDTO);

        List<Rating> ratings;
        if (select.equals("all"))
            ratings = ratingService.getAllRatingByProductId(id, (page - 1) * 10);
        else {
            if (!select.equals("image")) {
                ratings = ratingService.getRatingByProductIdAndStar(id, Integer.parseInt(select), (page - 1) * 10);
            } else {
                ratings = ratingService.getRatingByProductIdHasImage(id, (page - 1) * 10);
            }
        }

        assert ratings != null;
        ratingProductDTO.setData(ratings.stream()
                .map(value -> new RatingDTOMapper().mapRow(value)).collect(Collectors.toList()));
        return ResponseEntity.ok().body(ratingProductDTO);
    }
}
