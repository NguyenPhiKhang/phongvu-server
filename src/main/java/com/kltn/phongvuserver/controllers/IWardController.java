package com.kltn.phongvuserver.controllers;

import com.kltn.phongvuserver.models.Ward;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface IWardController {
    @GetMapping("/province/district/{districtId}/get-wards")
    List<Ward> getWardByDistrict(@PathVariable("districtId") int districtId);
}
