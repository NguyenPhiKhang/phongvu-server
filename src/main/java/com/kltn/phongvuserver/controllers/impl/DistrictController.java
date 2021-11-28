package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IDistrictController;
import com.kltn.phongvuserver.models.District;
import com.kltn.phongvuserver.services.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@Transactional(rollbackFor = Throwable.class)
public class DistrictController implements IDistrictController {
    @Autowired
    private IDistrictService districtService;

    @Override
    public List<District> getDistrictByProvince(int provinceId) {
        return districtService.getDistrictByProvince(provinceId);
    }
}
