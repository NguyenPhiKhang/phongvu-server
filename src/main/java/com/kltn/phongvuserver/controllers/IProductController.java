package com.kltn.phongvuserver.controllers;

import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface IProductController {
    @GetMapping("/cat/{idCategory}/products")
    List<ProductItemDTO> getProductsByCategory(
            @PathVariable("idCategory") int idCategory,
            @RequestParam(value = "demand", defaultValue = "0") int demand,
            @RequestParam(value = "brand", defaultValue = "0") int brand,
            @RequestParam(value = "p", defaultValue = "1") int page);
}
