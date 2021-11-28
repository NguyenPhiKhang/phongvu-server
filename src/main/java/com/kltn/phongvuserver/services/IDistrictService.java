package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.District;

import java.util.List;

public interface IDistrictService {
    List<District> getDistrictByProvince(int province_id);
}
