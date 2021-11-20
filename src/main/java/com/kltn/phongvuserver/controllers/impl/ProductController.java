package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IProductController;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import com.kltn.phongvuserver.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController implements IProductController {
    @Autowired
    private IProductService productService;

    @Override
    public List<ProductItemDTO> getProductsByCategory(int idCategory, int demand, int brand, int page) {
        return productService.getProductsByCategory(idCategory, demand, brand, page);
    }
}