package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Shipping;

import java.util.List;

public interface IShippingService {
    List<Shipping> getUnitShipping();
    Shipping getShippingById(int id);
}
