package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Address;
import com.kltn.phongvuserver.models.dto.AddressDTO;

import java.util.List;

public interface IAddressService {
    void addAddressForUser(Address address, int userId);
    String updateAddressForUser(Address address);
    String deleteAddressForUser(int addressId);
    List<Address> getAddressOfUser(int userId);
    Address getAddressById(int id);
    AddressDTO getAddressForOrder(int userId);
}
