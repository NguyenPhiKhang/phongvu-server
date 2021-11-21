package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.DataImage;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.ProductAttribute;
import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.dto.*;
import com.kltn.phongvuserver.utils.CommonUtil;
import com.kltn.phongvuserver.utils.EnvUtil;
import com.kltn.phongvuserver.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProductDetailDTOMapper implements RowMapper<ProductDetailDTO, Product> {
    @Autowired
    private RatingDTOMapper ratingDTOMapper;

    @Autowired
    private EnvUtil envUtil;

    @Override
    public ProductDetailDTO mapRow(Product product) {
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        productDetailDTO.setId(product.getId());
        productDetailDTO.setName(product.getName());
        productDetailDTO.setFreeShip(product.isFreeShip());
        productDetailDTO.setPromotionPercent(product.getPromotionPercent());
        productDetailDTO.setActive(product.isActive());
        productDetailDTO.setDescription(product.getDescription());
        productDetailDTO.setShortDescription(product.getShortDescription());
        productDetailDTO.setSku(product.getSku());
        productDetailDTO.setVisibility(product.isVisibility());
        productDetailDTO.setLiked(product.isLiked());
        productDetailDTO.setOrderCount(product.getOrderCount());
        productDetailDTO.setTotalQuantity(product.getQuantity());
        productDetailDTO.setPriceOriginal(product.getPriceOriginal());
        productDetailDTO.setPriceFinal(CommonUtil.calcPriceFinal(product.getPriceOriginal(), product.getPromotionPercent()));
        productDetailDTO.setCategory(product.getCategory());
        productDetailDTO.setRatingStar(product.getRatingStar());
        productDetailDTO.setBrand(product.getBrand());

        RatingProductDTO ratingProductDTO = new RatingProductDTO();
        Set<Rating> ratings = product.getRatings().stream().sorted(Comparator.comparing(Rating::getTimeUpdated)).limit(2).collect(Collectors.toSet());;

        List<RatingDTO> ratingDTOList = new ArrayList<>();

        for(Rating rating: ratings){
            RatingDTO ratingDTO = ratingDTOMapper.mapRow(rating);
            ratingDTOList.add(ratingDTO);
        }

        CalcRatingStarDTO calcRatingStarDTO = CommonUtil.calcRatingStarProduct(product.getRatingStar());

        ratingProductDTO.setData(ratingDTOList);
        ratingProductDTO.setTotalCount(new CountRatingProductDTO(calcRatingStarDTO.getTotalStar(), calcRatingStarDTO.getPercentStar()));
        productDetailDTO.setRatings(ratingProductDTO);

        List<String> urlImages = product.getDataImages().stream().sorted(Comparator.comparing(DataImage::getId).reversed())
                .map(d -> {
                    try {
                        return ImageUtil.getUrlImage(d, envUtil.getServerUrlPrefi());
                    } catch (UnknownHostException e) {
                        return ImageUtil.DEFAULT_PRODUCT_IMAGE_URL;
                    }
                }).collect(Collectors.toList());

        productDetailDTO.setListImages(urlImages);

        productDetailDTO.setAttributes(product.getProductAttributes().stream().sorted(Comparator.comparing(ProductAttribute::getId)).collect(Collectors.toList()));

        return productDetailDTO;
    }
}
