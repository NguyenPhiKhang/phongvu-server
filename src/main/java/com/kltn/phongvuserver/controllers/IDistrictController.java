package com.kltn.phongvuserver.controllers;

import com.kltn.phongvuserver.models.District;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface IDistrictController {
    @GetMapping("/province/{provinceId}/get-districts")
    List<District> getDistrictByProvince(@PathVariable int provinceId);
}
