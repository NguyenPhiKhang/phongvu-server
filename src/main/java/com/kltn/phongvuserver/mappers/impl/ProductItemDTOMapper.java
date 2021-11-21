package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.DataImage;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.RatingStar;
import com.kltn.phongvuserver.models.dto.CalcRatingStarDTO;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import com.kltn.phongvuserver.utils.CommonUtil;
import com.kltn.phongvuserver.utils.EnvUtil;
import com.kltn.phongvuserver.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.Comparator;

@Component
public class ProductItemDTOMapper implements RowMapper<ProductItemDTO, Product> {
    @Autowired
    private EnvUtil envUtil;

    @Override
    public ProductItemDTO mapRow(Product product) {
        ProductItemDTO productItemDTO = new ProductItemDTO();
        productItemDTO.setId(product.getId());
        productItemDTO.setName(product.getName());
        productItemDTO.setFreeShip(product.isFreeShip());
        productItemDTO.setPriceOriginal(product.getPriceOriginal());
        productItemDTO.setPromotionPercent(product.getPromotionPercent());
        productItemDTO.setPriceFinal(CommonUtil.calcPriceFinal(product.getPriceOriginal(), product.getPromotionPercent()));
        productItemDTO.setOrderCount(product.getOrderCount());
        productItemDTO.setQuantity(product.getQuantity());
        productItemDTO.setShortDescription(product.getShortDescription());

        RatingStar ratingStar = product.getRatingStar();
        CalcRatingStarDTO calcRatingStarDTO = CommonUtil.calcRatingStarProduct(ratingStar);
        productItemDTO.setPercentStar(calcRatingStarDTO.getPercentStar());
        productItemDTO.setCountRating(calcRatingStarDTO.getTotalStar());

        String urlImage = product.getDataImages().stream().sorted(Comparator.comparing(DataImage::getId).reversed())
                .map(d -> {
                    try {
                        return ImageUtil.getUrlImage(d, envUtil.getServerUrlPrefi());
                    } catch (UnknownHostException e) {
                        return ImageUtil.DEFAULT_PRODUCT_IMAGE_URL;
                    }
                })
                .findFirst().orElse(ImageUtil.DEFAULT_PRODUCT_IMAGE_URL);

        productItemDTO.setImgUrl(urlImage);

        return productItemDTO;
    }
}
