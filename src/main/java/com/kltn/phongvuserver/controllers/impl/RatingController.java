package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IRatingController;
import com.kltn.phongvuserver.mappers.impl.CardMyRatingDTOMapper;
import com.kltn.phongvuserver.mappers.impl.RatingDTOMapper;
import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.RatingStar;
import com.kltn.phongvuserver.models.dto.*;
import com.kltn.phongvuserver.services.IProductService;
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
    private IProductService productService;

    @Autowired
    private RatingDTOMapper ratingDTOMapper;

    @Autowired
    private CardMyRatingDTOMapper cardMyRatingDTOMapper;

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


    @Override
    public ResponseEntity<String> reviewProduct(int user_id, InputReviewProductDTO input_review) {
        try {
            productService.reviewProduct(user_id, input_review);
            return ResponseEntity.ok().body("review succeeded!!!");
        } catch (Exception e) {
            return ResponseEntity.ok().body("review failed!!!");
        }
    }

    @Override
    public List<CardMyRatingDTO> getMyRatings(int userId, int star, int page, int pageSize) {
        return ratingService.getRatingByUserAndStar(userId, star, page, pageSize)
                .stream()
                .map(r-> cardMyRatingDTOMapper.mapRow(r))
                .collect(Collectors.toList());
    }

    @Override
    public CountRatingProductDTO countStarRatingByUser(int userId) {
        return ratingService.countStarRatingByUser(userId);
    }
}
