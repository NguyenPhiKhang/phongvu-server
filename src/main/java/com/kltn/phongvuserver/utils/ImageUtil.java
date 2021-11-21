package com.kltn.phongvuserver.utils;

import com.kltn.phongvuserver.models.DataImage;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;

public class ImageUtil {
    public static final String DEFAULT_PRODUCT_IMAGE_URL = "https://developers.google.com/maps/documentation/streetview/images/error-image-generic.png?hl=vi";
    public static final String DEFAULT_USER_IMAGE_URL = "https://media3.scdn.vn/images/apps/icon_user_default.png";

    public static String getUrlImage(DataImage image, String serverUrl) {
        return image.getData() == null ? image.getLink()
                : serverUrl
                .concat("/api/v1/image/").concat(image.getId());
    }
}
