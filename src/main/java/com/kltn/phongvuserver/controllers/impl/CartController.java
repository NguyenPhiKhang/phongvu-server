package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.ICartController;
import com.kltn.phongvuserver.services.ICartService;
import com.kltn.phongvuserver.services.IImageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Transactional(rollbackFor = Throwable.class)
public class CartController implements ICartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    private IImageDataService imageDataService;

    @Override
    public String addProductIntoCart(int userId, int productId, int quantity) {
        cartService.addProductInCart(userId, productId, quantity);
        return "add success";
    }

    @Override
    public String removeProductInCart(int cartItemId) {
        return cartService.removeProductInCart(cartItemId);
    }

    @Override
    public String updateProductInCart(int userId, int cartItemId, int quantity) {
        cartService.updateProductInCart(userId, cartItemId, quantity);
        return "update success";
    }
}
