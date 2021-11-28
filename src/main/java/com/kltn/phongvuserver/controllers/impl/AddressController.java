package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IAddressController;
import com.kltn.phongvuserver.mappers.impl.AddressDTOMapper;
import com.kltn.phongvuserver.models.Address;
import com.kltn.phongvuserver.models.dto.AddressDTO;
import com.kltn.phongvuserver.services.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional(rollbackFor = Throwable.class)
@RequestMapping("/api/v1/")
public class AddressController implements IAddressController {
    @Autowired
    private IAddressService addressService;

    @Autowired
    private AddressDTOMapper addressDTOMapper;

    @Override
    public String addAddressForUser(int userId, int wardId, Address address) {
        addressService.addAddressForUser(address, userId, wardId);
        return "add success";
    }

    @Override
    public List<AddressDTO> getAddressesOfUser(int userId) {
        return addressService.getAddressOfUser(userId).stream().map(value -> addressDTOMapper.mapRow(value)).collect(Collectors.toList());
    }

    @Override
    public String removeAddressById(int addressId) {
        return addressService.deleteAddressForUser(addressId);
    }

    @Override
    public String updateAddress(Address address) {
        return addressService.updateAddressForUser(address);
    }
}
