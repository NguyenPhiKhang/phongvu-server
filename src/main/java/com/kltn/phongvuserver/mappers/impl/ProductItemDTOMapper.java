package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.RatingStar;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ProductItemDTOMapper implements RowMapper<ProductItemDTO, Product> {
//    @Autowired
//    private

    @Override
    public ProductItemDTO mapRow(Product product) {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setId(product.getId());
        productItemDTO.setName(product.getName());
        productItemDTO.setFreeShip(product.isFreeShip());
        productItemDTO.setPrice(product.getPriceOriginal());
        productItemDTO.setPromotionPercent(product.getPromotionPercent());
        productItemDTO.setFinalPrice(product.getPriceOriginal().multiply(BigDecimal.valueOf(1.0 - (double) product.getPromotionPercent() / 100.0)));
        productItemDTO.setOrderCount(product.getOrderCount());
        productItemDTO.setQuantity(product.getQuantity());
        productItemDTO.setShortDescription(product.getShortDescription());

        RatingStar ratingStar = product.getRatingStar();
        if (ratingStar != null) {
            int totalStar = ratingStar.getStar1() + ratingStar.getStar2() + ratingStar.getStar3() + ratingStar.getStar4() + ratingStar.getStar5();
            float percentStar = totalStar > 0 ? (float) (ratingStar.getStar1() + ratingStar.getStar2() * 2 + ratingStar.getStar3() * 3 + ratingStar.getStar4() * 4 + ratingStar.getStar5() * 5)
                    / totalStar : 0;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            productItemDTO.setPercentStar(Float.parseFloat(decimalFormat.format(percentStar)));
            productItemDTO.setCountRating(totalStar);
        }


        return productItemDTO;
    }
}
