package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IRatingController;
import com.kltn.phongvuserver.mappers.impl.RatingDTOMapper;
import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.RatingStar;
import com.kltn.phongvuserver.models.dto.CalcRatingStarDTO;
import com.kltn.phongvuserver.models.dto.CountRatingProductDTO;
import com.kltn.phongvuserver.models.dto.RatingProductDTO;
import com.kltn.phongvuserver.services.IRatingService;
import com.kltn.phongvuserver.services.IRatingStarService;
import com.kltn.phongvuserver.utils.CommonUtil;
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

    @Autowired
    private IRatingStarService ratingStarService;

    @Autowired
    private RatingDTOMapper ratingDTOMapper;

    @Override
    public ResponseEntity<String> autoRating() {
        ratingService.autoRating();
        return ResponseEntity.ok().body("done");
    }

    @Override
    public ResponseEntity<RatingProductDTO> getRatingByProduct(int id, String select, int page) {
        RatingProductDTO ratingProductDTO = new RatingProductDTO();

        RatingStar ratingStar = ratingStarService.getRatingStarByProductId(id);

        CountRatingProductDTO countRatingProductDTO = new CountRatingProductDTO(
                ratingStar.getStar1(),
                ratingStar.getStar2(),
                ratingStar.getStar3(),
                ratingStar.getStar4(),
                ratingStar.getStar5()
        );
        CalcRatingStarDTO calcRatingStarDTO = CommonUtil.calcRatingStarProduct(ratingStar);
        countRatingProductDTO.setTotalImage(ratingService.countRatingByImageOfProduct(id));
        countRatingProductDTO.setTotalAll(calcRatingStarDTO.getTotalStar());
        countRatingProductDTO.setPercentStar(calcRatingStarDTO.getPercentStar());

        ratingProductDTO.setTotalCount(countRatingProductDTO);

        List<Rating> ratings;
        if (select.equals("all"))
            ratings = ratingService.getRatingsByProductId(id, CommonUtil.getPageForNativeQueryIsTrue(page, 20));
        else {
            if (!select.equals("image")) {
                ratings = ratingService.getRatingByProductIdAndStar(id, Integer.parseInt(select), CommonUtil.getPageForNativeQueryIsTrue(page, 20));
            } else {
                ratings = ratingService.getRatingByProductIdHasImage(id, CommonUtil.getPageForNativeQueryIsTrue(page, 20));
            }
        }

        assert ratings != null;
        ratingProductDTO.setData(ratings.stream()
                .map(value -> ratingDTOMapper.mapRow(value)).collect(Collectors.toList()));
        return ResponseEntity.ok().body(ratingProductDTO);
    }
}
