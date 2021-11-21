package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IFavoriteController;
import com.kltn.phongvuserver.mappers.impl.ProductItemDTOMapper;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import com.kltn.phongvuserver.services.IFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Transactional(rollbackFor = Throwable.class)
public class FavoriteController implements IFavoriteController {
    @Autowired
    private IFavoriteService favoriteProductService;

    @Override
    public String createOrUpdateFavoriteOfUser(int userId, int productId) {
        favoriteProductService.updateFavoriteOfUser(userId, productId);
        return "Updated!";
    }

    @Override
    public List<ProductItemDTO> getListFavoriteProductByUser(int userId, int page) {
        return favoriteProductService.getListFavoriteProductByUser(userId, page);
    }
}
