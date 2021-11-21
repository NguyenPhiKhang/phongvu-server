package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Cart;
import com.kltn.phongvuserver.models.CartItem;
import com.kltn.phongvuserver.models.dto.CartItemDTO;

import java.util.List;

public interface ICartItemService {

    CartItem getCartItemByCartAndProduct(int cartId, int productId);

    boolean checkExistsCartItemById(int id);

    CartItem save(CartItem cartItem);

    CartItem save(int productId, int quantity, Cart cart);

    List<CartItemDTO> getCartItemByUser(int userId);

    CartItem getCartItemById(int cartItemId);

    String removeCartItemById(int cartItemId);
}

