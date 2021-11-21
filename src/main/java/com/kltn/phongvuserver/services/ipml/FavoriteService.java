package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.mappers.impl.ProductItemDTOMapper;
import com.kltn.phongvuserver.models.Favorite;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import com.kltn.phongvuserver.models.embeddedID.ProductUserKey;
import com.kltn.phongvuserver.repositories.FavoriteRepository;
import com.kltn.phongvuserver.services.IFavoriteService;
import com.kltn.phongvuserver.services.IProductService;
import com.kltn.phongvuserver.services.IUserService;
import com.kltn.phongvuserver.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class FavoriteService implements IFavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ProductItemDTOMapper productItemDTOMapper;

    @Override
    public void updateFavoriteOfUser(int userId, int productId) {
        if (favoriteRepository.existsFavoriteProductByIdProductIdAndIdUserId(productId, userId)) {
            Favorite favoriteProduct = favoriteRepository.findFavoriteProductByIdProductIdAndIdUserId(productId, userId);
            favoriteProduct.setLiked(!favoriteProduct.isLiked());
            favoriteProduct.setTimeUpdated(new Timestamp(System.currentTimeMillis()));

            favoriteRepository.save(favoriteProduct);
        } else {
            Favorite favoriteProduct = new Favorite();
            favoriteProduct.setLiked(true);
            favoriteProduct.setTimeUpdated(new Timestamp(System.currentTimeMillis()));
            favoriteProduct.setProduct(productService.findProductByIdVisibleTrue(productId));
            favoriteProduct.setUser(userService.getUserById(userId));
            favoriteProduct.setId(new ProductUserKey(productId, userId));

            favoriteRepository.save(favoriteProduct);
        }
    }

    @Override
    public List<ProductItemDTO> getListFavoriteProductByUser(int userId, int page) {
        return favoriteRepository.getFavoriteByUserFetchForGetListProduct(userId, CommonUtil.getPageForNativeQueryIsFalse(page, 20))
                .stream().map(value -> {
//            FlashSaleProduct flashSaleProduct = flashSaleProductService.getProductFlashSaleInProgress(value.getProduct().getId());
//
//            if (flashSaleProduct != null)
//                productItemDTO.setPromotionPercent(flashSaleProduct.getPercentDiscount());
//
                    return productItemDTOMapper.mapRow(value.getProduct());

                }).collect(Collectors.toList());
    }

    @Override
    public boolean checkUserLikedProduct(int userId, int productId) {
        return favoriteRepository.existsFavoriteByIdProductIdAndIdUserIdAndLikedTrue(productId, userId);
    }
}
