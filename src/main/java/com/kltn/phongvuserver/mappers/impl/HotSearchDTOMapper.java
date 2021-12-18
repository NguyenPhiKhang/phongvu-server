package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.DataImage;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.recommendsystem.HotSearchDTO;
import com.kltn.phongvuserver.utils.EnvUtil;
import com.kltn.phongvuserver.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.Comparator;
import java.util.Map;

@Component
public class HotSearchDTOMapper implements RowMapper<HotSearchDTO, Map.Entry<Product, String>> {
    @Autowired
    private EnvUtil envUtil;

    @Override
    public HotSearchDTO mapRow(Map.Entry<Product, String> productStringHashMap) {
        try {
            HotSearchDTO hotSearchDTO = new HotSearchDTO();

            Product product = productStringHashMap.getKey();

            String urlImage = product.getDataImages().stream().sorted(Comparator.comparing(DataImage::getId).reversed())
                    .map(d -> {
                        try {
                            return ImageUtil.getUrlImage(d, envUtil.getServerUrlPrefi());
                        } catch (UnknownHostException e) {
                            return ImageUtil.DEFAULT_PRODUCT_IMAGE_URL;
                        }
                    })
                    .findFirst().orElse(ImageUtil.DEFAULT_PRODUCT_IMAGE_URL);

            hotSearchDTO.setKeyword(productStringHashMap.getValue());
            return hotSearchDTO;
        } catch (Exception ex) {
            return null;
        }
    }
}
