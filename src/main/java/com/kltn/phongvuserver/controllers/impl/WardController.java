package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IWardController;
import com.kltn.phongvuserver.models.Ward;
import com.kltn.phongvuserver.services.IWardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class WardController implements IWardController {
    @Autowired
    private IWardService wardService;

    @Override
    public List<Ward> getWardByDistrict(int districtId) {
        return wardService.getWardByDistrict(districtId);
    }
}
