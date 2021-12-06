package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.Payment;
import com.kltn.phongvuserver.repositories.PaymentRepository;
import com.kltn.phongvuserver.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment getPaymentById(int id) {
        return paymentRepository.findById(id).orElse(null);
    }
}
