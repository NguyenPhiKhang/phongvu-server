package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IShippingController;
import com.kltn.phongvuserver.models.Shipping;
import com.kltn.phongvuserver.services.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Transactional(rollbackFor = Throwable.class)
public class ShippingController implements IShippingController {
    @Autowired
    private IShippingService shippingService;

    @Override
    public List<Shipping> getUnitShipping() {
        return shippingService.getUnitShipping();
    }
}
