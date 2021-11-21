package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.User;
import com.kltn.phongvuserver.models.dto.RatingDTO;
import com.kltn.phongvuserver.utils.EnvUtil;
import com.kltn.phongvuserver.utils.ImageUtil;
import com.kltn.phongvuserver.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.stream.Collectors;

@Component
public class RatingDTOMapper implements RowMapper<RatingDTO, Rating> {
    @Autowired
    private EnvUtil envUtil;

    @Autowired
    private FileRatingDTOMapper fileRatingDTOMapper;

    @Override
    public RatingDTO mapRow(Rating rating) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setId(rating.getId());
        ratingDTO.setComment(rating.getComment());
        User user = rating.getUser();
        ratingDTO.setUserId(user.getId());
        ratingDTO.setStar(rating.getStar());
        ratingDTO.setTimeUpdated(StringUtil.convertTimestampToString(rating.getTimeUpdated()));
        try {
            ratingDTO.setImageAvatar(ImageUtil.getUrlImage(user.getImageAvatar(), envUtil.getServerUrlPrefi()));
        } catch (UnknownHostException | NullPointerException e) {
            ratingDTO.setImageAvatar(ImageUtil.DEFAULT_USER_IMAGE_URL);
        }

        if (rating.isIncognito())
            ratingDTO.setUserName(StringUtil.incognitoName(user.getName()));
        else ratingDTO.setUserName(user.getName());

        ratingDTO.setFileRating(rating.getDataImages().stream().map(v -> fileRatingDTOMapper.mapRow(v)).collect(Collectors.toList()));

        return ratingDTO;
    }
}
