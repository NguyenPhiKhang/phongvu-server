package com.kltn.phongvuserver.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kltn.phongvuserver.models.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ProductItemDTO {
    private int id;
    private String name;
    private boolean freeShip;
    private BigDecimal price;
    private float promotionPercent;
    private BigDecimal finalPrice;
    private int orderCount;
    private String imgUrl;
    private float percentStar;
    private int countRating;
    private int quantity;
    private String shortDescription;
}
