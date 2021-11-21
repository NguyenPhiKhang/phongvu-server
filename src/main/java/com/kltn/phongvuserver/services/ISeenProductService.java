package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.SeenProduct;

import java.util.List;

public interface ISeenProductService {
    void createOrUpdateSeenProduct(int userId, Product product);
    List<SeenProduct> getListSeenProduct(int userId);
}
