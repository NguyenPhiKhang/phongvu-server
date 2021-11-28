package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.District;
import com.kltn.phongvuserver.repositories.DistrictRepository;
import com.kltn.phongvuserver.services.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class DistrictService implements IDistrictService {
    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public List<District> getDistrictByProvince(int province_id) {
        return districtRepository.findDistrictByProvinceIdOrderByName(province_id);
    }
}
