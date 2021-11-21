package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.Cart;
import com.kltn.phongvuserver.models.CartItem;
import com.kltn.phongvuserver.models.DataImage;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.dto.CartItemDTO;
import com.kltn.phongvuserver.utils.CommonUtil;
import com.kltn.phongvuserver.utils.EnvUtil;
import com.kltn.phongvuserver.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.Comparator;

@Component
public class CartItemDTOMapper implements RowMapper<CartItemDTO, CartItem> {
    @Autowired
    private EnvUtil envUtil;

    @Override
    public CartItemDTO mapRow(CartItem cartItem) {
        try {
            Product product = cartItem.getProduct();
            Cart cart = cartItem.getCart();

            CartItemDTO cartItemDTO = new CartItemDTO();
            cartItemDTO.setCartItemId(cartItem.getId());
            cartItemDTO.setCartId(cart.getId());
            cartItemDTO.setProductId(product.getId());
            cartItemDTO.setNameProduct(product.getName());
            cartItemDTO.setQuantityProduct(product.getQuantity());
            cartItemDTO.setQuantityBuy(cartItem.getQuantity());
            cartItemDTO.setDiscount(product.getPromotionPercent());
            cartItemDTO.setPriceOriginal(product.getPriceOriginal());
            cartItemDTO.setPriceFinal(CommonUtil.calcPriceFinal(product.getPriceOriginal(), product.getPromotionPercent()));

            String urlImage = product.getDataImages().stream().sorted(Comparator.comparing(DataImage::getId).reversed())
                    .map(d -> {
                        try {
                            return ImageUtil.getUrlImage(d, envUtil.getServerUrlPrefi());
                        } catch (UnknownHostException e) {
                            return ImageUtil.DEFAULT_PRODUCT_IMAGE_URL;
                        }
                    })
                    .findFirst().orElse(ImageUtil.DEFAULT_PRODUCT_IMAGE_URL);

            cartItemDTO.setImageUrl(urlImage);

            return cartItemDTO;

        } catch (Exception ex) {
            return null;
        }
    }
}
