package com.kltn.phongvuserver.controllers;

import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface IFavoriteController {
    @PostMapping("/{userId}/{productId}/update-favorite")
    String createOrUpdateFavoriteOfUser(@PathVariable("userId") int userId, @PathVariable("productId") int productId);

    @GetMapping("/{userId}/get-favorite-products")
    List<ProductItemDTO> getListFavoriteProductByUser(@PathVariable("userId") int userId, @RequestParam(value = "p", defaultValue = "1") int page,
                                                      @RequestParam(value = "p_size", defaultValue = "20") int pageSize);
}
