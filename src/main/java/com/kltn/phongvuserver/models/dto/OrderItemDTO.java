package com.kltn.phongvuserver.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDTO {
    private int id;
    private int productId;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private int quantity;
    private String name;
    private String imageUrl;
    private boolean reviewStatus;
}
