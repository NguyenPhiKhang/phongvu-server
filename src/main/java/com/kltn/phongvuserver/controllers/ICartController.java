package com.kltn.phongvuserver.controllers;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface ICartController {
    @PostMapping("/cart/{userId}/{productId}/add")
    String addProductIntoCart(@PathVariable("userId") int userId, @PathVariable("productId") int productId, @RequestParam("qty") int quantity);

    @DeleteMapping("/cart/{cartItemId}/remove")
    String removeProductInCart(@PathVariable int cartItemId);

    @DeleteMapping("/cart/remove-cart-items")
    String removeProductsInCart(@RequestParam("items") String listCartItem);

    @PutMapping("/cart/{userId}/{cartItemId}/update")
    String updateProductInCart(@PathVariable("userId") int userId, @PathVariable("cartItemId") int cartItemId, @RequestParam("qty") int quantity);
}
