package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.DataImage;
import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.dto.CardMyRatingDTO;
import com.kltn.phongvuserver.utils.EnvUtil;
import com.kltn.phongvuserver.utils.ImageUtil;
import com.kltn.phongvuserver.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class CardMyRatingDTOMapper implements RowMapper<CardMyRatingDTO, Rating> {
    @Autowired
    private EnvUtil envUtil;

    @Autowired
    private FileRatingDTOMapper fileRatingDTOMapper;

    @Override
    public CardMyRatingDTO mapRow(Rating rating) {
        try{
            CardMyRatingDTO cardMyRatingDTO = new CardMyRatingDTO();
            cardMyRatingDTO.setId(rating.getId());
            cardMyRatingDTO.setStar(rating.getStar());
            cardMyRatingDTO.setId(rating.getProduct().getId());
            cardMyRatingDTO.setNameProduct(rating.getProduct().getName());
            cardMyRatingDTO.setComment(rating.getComment());
            cardMyRatingDTO.setTimeUpdated(StringUtil.convertTimestampToString(rating.getTimeUpdated()));

            String urlImage = rating.getProduct().getDataImages().stream().sorted(Comparator.comparing(DataImage::getId).reversed())
                    .map(d -> {
                        try {
                            return ImageUtil.getUrlImage(d, envUtil.getServerUrlPrefi());
                        } catch (UnknownHostException e) {
                            return ImageUtil.DEFAULT_PRODUCT_IMAGE_URL;
                        }
                    })
                    .findFirst().orElse(ImageUtil.DEFAULT_PRODUCT_IMAGE_URL);

            cardMyRatingDTO.setImageProduct(urlImage);
            cardMyRatingDTO.setFileRating(rating.getDataImages().stream().map(v -> fileRatingDTOMapper.mapRow(v)).collect(Collectors.toList()));

            return cardMyRatingDTO;
        }catch (Exception ex){
            return null;
        }
    }
}
