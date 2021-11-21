package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.mappers.impl.CartItemDTOMapper;
import com.kltn.phongvuserver.models.Cart;
import com.kltn.phongvuserver.models.CartItem;
import com.kltn.phongvuserver.models.dto.CartItemDTO;
import com.kltn.phongvuserver.repositories.CartItemRepository;
import com.kltn.phongvuserver.services.ICartItemService;
import com.kltn.phongvuserver.services.ICartService;
import com.kltn.phongvuserver.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class CartItemService implements ICartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private IProductService productService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private CartItemDTOMapper cartItemDTOMapper;

    @Override
    public CartItem getCartItemByCartAndProduct(int cartId, int productId) {
        return cartItemRepository.findCartItemByProductIdAndCartId(productId, cartId);
    }

    @Override
    public boolean checkExistsCartItemById(int id) {
        return cartItemRepository.existsCartItemById(id);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem save(int productId, int quantity, Cart cart) {
        CartItem cartItem = new CartItem();
        Random rd = new Random();
        int idCartItem;
        do {
            idCartItem = 100 + rd.nextInt(6000001);
        } while (cartItemRepository.existsCartItemById(idCartItem));

        cartItem.setId(idCartItem);
        cartItem.setQuantity(quantity);
        cartItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        cartItem.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        cartItem.setProduct(productService.findProductByIdVisibleTrue(productId));
        cartItem.setCart(cart);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItemDTO> getCartItemByUser(int userId) {
        return cartItemRepository.findCartItemByUserId(userId).stream().map(value->{
            //            FlashSaleProduct flashSaleProduct = flashSaleProductService.getProductFlashSaleInProgress(cartItemDTO.getProductId());

//            if (flashSaleProduct != null)
//                cartItemDTO.setDiscount(flashSaleProduct.getPercentDiscount());

            return cartItemDTOMapper.mapRow(value);
        }).collect(Collectors.toList());
    }

    @Override
    public CartItem getCartItemById(int cartItemId) {
        return cartItemRepository.findById(cartItemId).orElse(null);
    }

    @Override
    public String removeCartItemById(int cartItemId) {
        if (cartItemRepository.existsCartItemById(cartItemId)) {
            cartItemRepository.deleteById(cartItemId);
            return "Đã bỏ thành công sản phẩm khỏi giỏ hàng";
        } else {
            return "Không tồn tại product trong giỏ hàng";
        }
    }
}