package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.mappers.impl.ProductDetailDTOMapper;
import com.kltn.phongvuserver.mappers.impl.ProductItemDTOMapper;
import com.kltn.phongvuserver.models.*;
import com.kltn.phongvuserver.models.dto.InputReviewProductDTO;
import com.kltn.phongvuserver.models.dto.ProductDetailDTO;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import com.kltn.phongvuserver.repositories.CategoryRepository;
import com.kltn.phongvuserver.repositories.ProductRepository;
import com.kltn.phongvuserver.repositories.RatingRepository;
import com.kltn.phongvuserver.services.*;
import com.kltn.phongvuserver.utils.CategoryUtil;
import com.kltn.phongvuserver.utils.CommonUtil;
import com.kltn.phongvuserver.utils.ImageUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ProductService implements IProductService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ISeenProductService seenProductService;

    @Autowired
    private IFavoriteService favoriteService;

    @Autowired
    private IRatingService ratingService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IImageDataService imageDataService;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ProductItemDTOMapper productItemDTOMapper;

    @Autowired
    private ProductDetailDTOMapper productDetailDTOMapper;

    @Override
    public List<ProductItemDTO> getProductsByCategory(int idCategory, int demand, int brand, int page) {
        Category category = categoryRepository.findCategoryFetchSubCategories(idCategory);
        Set<Integer> listId = new HashSet<>();

        Pageable pageable = CommonUtil.getPageForNativeQueryIsFalse(page, 20);
        int pageNew = CommonUtil.getPageForNativeQueryIsTrue(page, 20);

        List<Product> products;
        if (category.getName().contains("nhu cầu") || category.getName().contains("thương hiệu")) {
            listId.add(category.getParentCategory().getId());
            CategoryUtil.getListIdCategory(category.getParentCategory(), listId);

            products = productRepository.findProductsByCategoryAndBrandOrDemand(listId, demand, brand, pageNew);
        } else {
            listId.add(category.getId());
            CategoryUtil.getListIdCategory(category, listId);

            products = productRepository.findProductsByCategoryAndBrand(listId, brand, pageable);
        }

        return products.stream().map(p -> productItemDTOMapper.mapRow(p)).collect(Collectors.toList());
    }

    @Override
    public ProductDetailDTO getProductDetailById(int id, int userId) {
        Product product = productRepository.findProductDetailById(id);

        if (userId != 0) {
            seenProductService.createOrUpdateSeenProduct(userId, product);
            if (favoriteService.checkUserLikedProduct(userId, id)) {
                product.setLiked(true);
            }
        }

        return productDetailDTOMapper.mapRow(product);
    }

    @Override
    public Product findProductByIdVisibleTrue(int id) {
        return productRepository.findByIdAndVisibilityTrue(id);
    }

    @Override
    public void reviewProduct(int userId, InputReviewProductDTO inputReview) {
        Rating rating = new Rating();
        rating.setStar(inputReview.getStar());
        rating.setComment(inputReview.getComment());
        rating.setUser(userService.getUserById(userId));
        OrderItem orderItem = orderItemService.getOrderItem(inputReview.getOrderItem());
        orderItem.setReviewStatus(true);
        orderItemService.save(orderItem);
        rating.setProduct(orderItem.getProduct());
        rating.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        rating.setTimeUpdated(new Timestamp(System.currentTimeMillis()));
        rating.setIncognito(inputReview.isIncognito());

        try {
            saveImage(rating, inputReview.getListFiles());
        } catch (RuntimeException ex) {
            ratingRepository.save(rating);
        }

    }

    private void saveImage(Rating rating, List<MultipartFile> files) {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        files.forEach(file -> {
            try {
                String fileName = ImageUtil.fileName(imageDataService, file);
                MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, file.getContentType(), file.getInputStream());
                multipartFiles.add(multipartFile);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        rating.addAllDataImages(imageDataService.storesImageData(multipartFiles));
        ratingRepository.save(rating);
    }
}
