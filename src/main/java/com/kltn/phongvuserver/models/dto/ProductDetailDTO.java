package com.kltn.phongvuserver.models.dto;


import com.kltn.phongvuserver.models.Brand;
import com.kltn.phongvuserver.models.Category;
import com.kltn.phongvuserver.models.ProductAttribute;
import com.kltn.phongvuserver.models.RatingStar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDetailDTO {
    private int id;
    private String name;
    private String sku;
    private String description;
    private String shortDescription;
    private boolean active;
    private boolean visibility;
    private float promotionPercent;
    private int orderCount;
    private boolean freeShip;
    private int totalQuantity;
    private List<String> listImages;
    private BigDecimal priceOriginal;
    private BigDecimal priceFinal;
    private boolean liked;
    private RatingProductDTO ratings;
    private Category category;
    private Brand brand;
    private RatingStar ratingStar;
    private List<ProductAttribute> attributes;
}
