package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.ISeenProductController;
import com.kltn.phongvuserver.mappers.impl.ProductItemDTOMapper;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.dto.ProductItemDTO;
import com.kltn.phongvuserver.repositories.ProductRepository;
import com.kltn.phongvuserver.repositories.SeenProductRepository;
import com.kltn.phongvuserver.services.ISeenProductService;
import com.kltn.phongvuserver.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class SeenProductController implements ISeenProductController {
    @Autowired
    private ISeenProductService seenProductService;

    @Autowired
    private ProductItemDTOMapper productItemDTOMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SeenProductRepository seenProductRepository;

    @Override
    public String createOrUpdateSeenProduct(int userId, int productId) {
        Product product = productRepository.findByIdAndVisibilityTrue(productId);
        seenProductService.createOrUpdateSeenProduct(userId, product);
        return "updated";
    }

    @Override
    public List<ProductItemDTO> getListSeenProduct(int userId, int page, int pageSize) {
        return seenProductRepository
                .findSeenProductByUserIdOrderByLastTimeDesc(userId, CommonUtil.getPageForNativeQueryIsFalse(page, pageSize)).stream().map(value-> productItemDTOMapper.mapRow(value.getProduct())).collect(Collectors.toList());
    }
}
