package com.kltn.phongvuserver.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class InputOrderItemDTO {
    private int productId;
    private int cartItemId;
    private String imageUrl;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private int quantity;
}
