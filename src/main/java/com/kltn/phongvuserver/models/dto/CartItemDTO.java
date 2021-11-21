package com.kltn.phongvuserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private int cartItemId;
    private int cartId;
    private int productId;
    private String nameProduct;
    private int quantityProduct;
    private String imageUrl;
    private float discount;
    private BigDecimal priceOriginal;
    private BigDecimal priceFinal;
    private int quantityBuy;
}