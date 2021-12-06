package com.kltn.phongvuserver.services;


import com.kltn.phongvuserver.models.Payment;

public interface IPaymentService {
    Payment getPaymentById(int id);
}
