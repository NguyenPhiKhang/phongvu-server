package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.mappers.impl.AddressDTOMapper;
import com.kltn.phongvuserver.models.Address;
import com.kltn.phongvuserver.models.dto.AddressDTO;
import com.kltn.phongvuserver.repositories.AddressRepository;
import com.kltn.phongvuserver.services.IAddressService;
import com.kltn.phongvuserver.services.IUserService;
import com.kltn.phongvuserver.services.IWardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Service
@Transactional(rollbackFor = Throwable.class)
public class AddressService implements IAddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private IWardService wardService;

    @Autowired
    private IUserService userService;

    @Autowired
    private AddressDTOMapper addressDTOMapper;

    @Override
    public void addAddressForUser(Address address, int userId) {
        Address newAddress = new Address(address.getSpecificAddress(), address.getName(),
                address.getNumberPhone(), address.isDefaultIs(), new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));

        int idAddress;
        Random rd = new Random();

        do {
            idAddress = 100 + rd.nextInt(6000001);
        } while (addressRepository.existsById(idAddress));

        newAddress.setId(idAddress);
        newAddress.setWard(wardService.getWardById(address.getWard().getId()));
        newAddress.setUser(userService.getUserById(userId));

        if (address.isDefaultIs())
            addressRepository.setDefaultIsFalse(userId);

        addressRepository.save(newAddress);
    }

    @Override
    public String updateAddressForUser(Address address) {
        Address addressUpdate = addressRepository.findById(address.getId()).orElse(null);

        if (addressUpdate != null) {
            addressUpdate.setName(address.getName());
            addressUpdate.setNumberPhone(address.getNumberPhone());
            addressUpdate.setDefaultIs(address.isDefaultIs());
            if (address.isDefaultIs()) {
                addressRepository.setDefaultIsFalse(addressUpdate.getUser().getId());
            }

            addressUpdate.setUpdateAt(new Timestamp(System.currentTimeMillis()));
            addressUpdate.setSpecificAddress(address.getSpecificAddress());
            addressUpdate.setWard(address.getWard().getId() == 0 ? null : address.getWard());

            addressRepository.save(addressUpdate);
            return "udpate địa chỉ thành công";
        } else return "không tồn tại địa chỉ";
    }

    @Override
    public String deleteAddressForUser(int addressId) {
        if (addressRepository.existsById(addressId)) {
            addressRepository.deleteById(addressId);
            return "Đã xoá thành công địa chỉ";
        } else {
            return "Không tồn tại địa chỉ";
        }
    }

    @Override
    public List<Address> getAddressOfUser(int userId) {
        return addressRepository.findAddressByUserIdOrderByUpdateAtFetchDesc(userId);
    }

    @Override
    public Address getAddressById(int id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public AddressDTO getAddressForOrder(int userId) {
        return addressDTOMapper.mapRow(addressRepository.findAddressByUserIdOrderByUpdateAtFetchDesc(userId)
                .stream().max(Comparator.comparing(Address::isDefaultIs).reversed()
                        .thenComparing(Address::getUpdateAt)).orElse(null));
    }
}
