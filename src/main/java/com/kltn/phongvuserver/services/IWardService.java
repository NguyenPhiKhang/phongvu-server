package com.kltn.phongvuserver.services;


import com.kltn.phongvuserver.models.Ward;

import java.util.List;

public interface IWardService {
    List<Ward> getWardByDistrict(int districtId);
    Ward getWardById(int wardId);
}
