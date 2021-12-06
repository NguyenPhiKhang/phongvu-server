package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.StatusOrder;
import com.kltn.phongvuserver.repositories.StatusOrderRepository;
import com.kltn.phongvuserver.services.IStatusOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusOrderService implements IStatusOrderService {
    @Autowired
    private StatusOrderRepository statusOrderRepository;

    @Override
    public StatusOrder getStatusOrderById(int id) {
        return statusOrderRepository.findById(id).orElse(null);
    }
}
