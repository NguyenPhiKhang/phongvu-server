package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IRatingController;
import com.kltn.phongvuserver.services.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RatingController implements IRatingController {
    @Autowired
    private IRatingService ratingService;

    @Override
    public ResponseEntity<String> autoRating() {
        ratingService.autoRating();
        return ResponseEntity.ok().body("done");
    }
}
