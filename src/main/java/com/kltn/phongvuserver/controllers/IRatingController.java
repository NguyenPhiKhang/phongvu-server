package com.kltn.phongvuserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface IRatingController {
    @PostMapping("/rating/auto-rating")
    ResponseEntity<String> autoRating();
}
