package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.ICartItemController;
import com.kltn.phongvuserver.models.dto.CartItemDTO;
import com.kltn.phongvuserver.services.ICartItemService;
import com.kltn.phongvuserver.services.IImageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Transactional(rollbackFor = Throwable.class)
public class CartItemController implements ICartItemController {
    @Autowired
    private ICartItemService cartItemService;

    @Autowired
    private IImageDataService imageDataService;

    @Override
    public List<CartItemDTO> getListProductInCart(int userId) {
        return cartItemService.getCartItemByUser(userId);
    }
}
