package com.kltn.phongvuserver.controllers;

import com.kltn.phongvuserver.models.dto.CardMyRatingDTO;
import com.kltn.phongvuserver.models.dto.CountRatingProductDTO;
import com.kltn.phongvuserver.models.dto.InputReviewProductDTO;
import com.kltn.phongvuserver.models.dto.RatingProductDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface IRatingController {

    @PostMapping("/rating/auto-rating")
    ResponseEntity<String> autoRating();

    @GetMapping("/rating/{productId}")
    ResponseEntity<RatingProductDTO> getRatingByProduct(@PathVariable("productId") int id,
                                                        @RequestParam(value = "select", defaultValue = "all") String select,
                                                        @RequestParam(value = "p", defaultValue = "1") int page,
                                                        @RequestParam(value = "p_size", defaultValue = "20") int pageSize);

    @RequestMapping(value = "/user/{userId}/review-product", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> reviewProduct(@PathVariable("userId") int user_id, @ModelAttribute("input_review") InputReviewProductDTO input_review);

    @RequestMapping(value = "/user/{userId}/my-ratings")
    List<CardMyRatingDTO> getMyRatings(@PathVariable("userId") int userId,
                                       @RequestParam(value = "star", defaultValue = "0") int star,
                                       @RequestParam(value = "p", defaultValue = "1") int page,
                                       @RequestParam(value = "p_size", defaultValue = "20") int pageSize);

    @GetMapping("/user/{userId}/count-star-my-ratings")
    CountRatingProductDTO countStarRatingByUser(@PathVariable int userId);
}
