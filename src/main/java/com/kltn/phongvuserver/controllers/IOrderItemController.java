package com.kltn.phongvuserver.controllers;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface IOrderItemController {

    @PutMapping("/order-item/{id}/update-review-status")
    @Modifying
    String updateReviewStatus(@PathVariable("id") int id, @RequestParam(value = "status", defaultValue = "1") int status);
}
