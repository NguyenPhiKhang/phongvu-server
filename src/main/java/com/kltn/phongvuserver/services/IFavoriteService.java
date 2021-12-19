package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Favorite;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;

import java.util.List;

public interface IFavoriteService {
    void updateFavoriteOfUser(int userId, int productId);
    List<ProductItemDTO> getListFavoriteProductByUser(int userId, int page, int pageSize);
    boolean checkUserLikedProduct(int userId, int productId);
}
