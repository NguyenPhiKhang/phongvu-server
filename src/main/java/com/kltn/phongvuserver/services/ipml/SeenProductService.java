package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.SeenProduct;
import com.kltn.phongvuserver.models.embeddedID.ProductUserKey;
import com.kltn.phongvuserver.repositories.SeenProductRepository;
import com.kltn.phongvuserver.services.IProductService;
import com.kltn.phongvuserver.services.ISeenProductService;
import com.kltn.phongvuserver.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class SeenProductService implements ISeenProductService {
    @Autowired
    private SeenProductRepository seenProductRepository;

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;

    @Override
    public void createOrUpdateSeenProduct(int userId, Product product) {
        if (seenProductRepository.existsSeenProductByIdProductIdAndIdUserId(product.getId(), userId)) {
            SeenProduct seenProduct = seenProductRepository.findSeenProductByIdProductIdAndIdUserId(product.getId(), userId);
            seenProduct.setCount(seenProduct.getCount() + 1);
            seenProduct.setLastTime(new Timestamp(System.currentTimeMillis()));

            seenProductRepository.save(seenProduct);
        } else {
            SeenProduct seenProduct = new SeenProduct();
            seenProduct.setCount(1);
            seenProduct.setLastTime(new Timestamp(System.currentTimeMillis()));
            seenProduct.setProduct(product);
            seenProduct.setUser(userService.getUserById(userId));
            seenProduct.setId(new ProductUserKey(product.getId(), userId));

            seenProductRepository.save(seenProduct);
        }
    }

    @Override
    public List<SeenProduct> getListSeenProduct(int userId) {
        return seenProductRepository.findSeenProductByUserAndLastTime(userId);
    }
}
