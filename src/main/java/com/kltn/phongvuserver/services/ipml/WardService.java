package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.Ward;
import com.kltn.phongvuserver.repositories.WardRepository;
import com.kltn.phongvuserver.services.IWardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class WardService implements IWardService {
    @Autowired
    private WardRepository wardRepository;

    @Override
    public List<Ward> getWardByDistrict(int districtId) {
        return wardRepository.findWardByDistrictIdOrderByName(districtId);
    }

    @Override
    public Ward getWardById(int wardId) {
        return wardRepository.findById(wardId).orElse(null);
    }
}
