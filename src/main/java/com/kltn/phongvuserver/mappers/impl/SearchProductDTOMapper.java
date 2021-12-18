package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.dto.SearchProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SearchProductDTOMapper implements RowMapper<SearchProductDTO, Map.Entry<Product, Double>> {
    @Autowired
    private ProductItemDTOMapper productItemDTOMapper;

    @Override
    public SearchProductDTO mapRow(Map.Entry<Product, Double> productDoubleEntry) {
        try {
            return new SearchProductDTO(productItemDTOMapper.mapRow(productDoubleEntry.getKey()), productDoubleEntry.getValue());
        } catch (Exception ex) {
            return null;
        }
    }
}
