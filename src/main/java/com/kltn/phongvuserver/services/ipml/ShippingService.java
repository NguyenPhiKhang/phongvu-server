package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.Shipping;
import com.kltn.phongvuserver.repositories.ShippingRepository;
import com.kltn.phongvuserver.services.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ShippingService implements IShippingService {
    @Autowired
    private ShippingRepository shippingRepository;

    @Override
    public List<Shipping> getUnitShipping() {
        return shippingRepository.findAll();
    }

    @Override
    public Shipping getShippingById(int id) {
        return shippingRepository.findById(id).orElse(null);
    }
}
