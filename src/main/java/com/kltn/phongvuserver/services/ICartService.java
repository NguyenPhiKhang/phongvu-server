package com.kltn.phongvuserver.services;

public interface ICartService {
    void addProductInCart(int userId, int productId, int quantity);
    String removeProductInCart(int cartItemId);
    void updateProductInCart(int userId, int cartItemId, int quantity);
}
