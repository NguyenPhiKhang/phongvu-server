package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.DataImage;
import com.kltn.phongvuserver.models.dto.FileRatingDTO;
import com.kltn.phongvuserver.utils.EnvUtil;
import com.kltn.phongvuserver.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

@Component
public class FileRatingDTOMapper implements RowMapper<FileRatingDTO, DataImage> {
    @Autowired
    private EnvUtil envUtil;

    @Override
    public FileRatingDTO mapRow(DataImage imageData) {
        try {
            FileRatingDTO fileRatingDTO = new FileRatingDTO();
            fileRatingDTO.setId(imageData.getId());
            fileRatingDTO.setContentType(imageData.getType().split("/")[0]);
            try {
                fileRatingDTO.setLinkUrl(ImageUtil.getUrlImage(imageData, envUtil.getServerUrlPrefi()));
            } catch (UnknownHostException | NullPointerException e) {
                fileRatingDTO.setLinkUrl(ImageUtil.DEFAULT_PRODUCT_IMAGE_URL);
            }
            return fileRatingDTO;
        } catch (Exception ex) {
            return null;
        }
    }
}
