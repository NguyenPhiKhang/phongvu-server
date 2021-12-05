package com.kltn.phongvuserver.controllers;

import com.kltn.phongvuserver.models.Address;
import com.kltn.phongvuserver.models.dto.AddressDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/default")
@CrossOrigin(value = {"http://localhost:3000", "https://adminfashion-shop.azurewebsites.net"})
public interface IAddressController {
    @PostMapping("/user/{userId}/add-address")
    String addAddressForUser(@PathVariable("userId") int userId, @RequestBody Address address);

    @GetMapping("/user/{userId}/get-addresses")
    List<AddressDTO> getAddressesOfUser(@PathVariable("userId") int userId);

    @DeleteMapping("/address/{addressId}/delete")
    String removeAddressById(@PathVariable("addressId") int addressId);

    @PutMapping("/address/update")
    String updateAddress(@RequestBody Address address);

    @GetMapping("/address/{userId}/get-address-order")
    AddressDTO getAddressForOrder(@PathVariable("userId") int userId);
}
