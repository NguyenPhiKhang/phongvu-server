package com.kltn.phongvuserver.controllers.impl;


import com.kltn.phongvuserver.controllers.IProvinceController;
import com.kltn.phongvuserver.models.Province;
import com.kltn.phongvuserver.services.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Transactional(rollbackFor = Throwable.class)
@RequestMapping("/api/v1")
public class ProvinceController implements IProvinceController {
    @Autowired
    private IProvinceService provinceService;

    @Override
    public List<Province> getAllProvince() {
        return provinceService.getAllProvince();
    }
}
