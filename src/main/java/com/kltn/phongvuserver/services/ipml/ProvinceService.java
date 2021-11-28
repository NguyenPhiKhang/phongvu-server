package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.Province;
import com.kltn.phongvuserver.repositories.ProvinceRepository;
import com.kltn.phongvuserver.services.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ProvinceService implements IProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public List<Province> getAllProvince() {
        return provinceRepository.findALlOrderByName();
    }
}
