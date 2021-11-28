package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.Address;
import com.kltn.phongvuserver.models.dto.AddressDTO;
import com.kltn.phongvuserver.utils.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class AddressDTOMapper implements RowMapper<AddressDTO, Address> {
    @Override
    public AddressDTO mapRow(Address address) {
        try {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setId(address.getId());
            addressDTO.setName(address.getName());
            addressDTO.setNumberPhone(address.getNumberPhone());
            addressDTO.setSpecificAddress(address.getSpecificAddress());
            addressDTO.setCreatedAt(StringUtil.convertTimestampToString(address.getCreatedAt()));
            addressDTO.setUpdateAt(StringUtil.convertTimestampToString(address.getCreatedAt()));

            if (address.getWard() != null) {
                addressDTO.setProvince(address.getWard().getProvince());
                addressDTO.setDistrict(address.getWard().getDistrict());
                addressDTO.setWard(address.getWard());
            }

            addressDTO.setDefaultIs(address.isDefaultIs());

            return addressDTO;
        } catch (Exception ex) {
            return null;
        }
    }
}
