package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.recommendsystem.RatingRSDTO;
import org.springframework.stereotype.Component;

@Component
public class RatingRSDTOMapper implements RowMapper<RatingRSDTO, Rating> {
    @Override
    public RatingRSDTO mapRow(Rating rating) {
        try{
            return new RatingRSDTO(rating.getUser().getId(), rating.getProduct().getId(), rating.getStar());
        }catch (Exception ex){
            return null;
        }
    }
}
