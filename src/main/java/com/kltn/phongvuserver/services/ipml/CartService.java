package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.Cart;
import com.kltn.phongvuserver.models.CartItem;
import com.kltn.phongvuserver.repositories.CartRepository;
import com.kltn.phongvuserver.services.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Random;
import java.util.Set;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CartService implements ICartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Override
    public void addProductInCart(int userId, int productId, int quantity) {
        Cart cart = cartRepository.findCartByUserId(userId);

        if (cart != null) {
            CartItem cartItem = cartItemService.getCartItemByCartAndProduct(cart.getId(), productId);
            if (cartItem != null) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                cartItemService.save(cartItem);
            } else {
                cartItemService.save(productId, quantity, cart);
            }
        } else {
            cart = new Cart();
            Random rd = new Random();

            int idCart;
            do {
                idCart = 100 + rd.nextInt(6000001);
            } while (cartRepository.existsById(idCart));

            cart.setId(idCart);
            cart.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            cart.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            cart.setUser(userService.getUserById(userId));

            cartItemService.save(productId, quantity, cart);
        }
    }

    @Override
    public String removeProductInCart(int cartItemId) {
        return cartItemService.removeCartItemById(cartItemId);
    }

    @Override
    public void updateProductInCart(int userId, int cartItemId, int quantity) {
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        cartItem.setQuantity(quantity);
        cartItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        cartItemService.save(cartItem);
    }
}

