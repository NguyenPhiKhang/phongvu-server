package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.Order;
import com.kltn.phongvuserver.models.dto.OrderManagerDTO;
import com.kltn.phongvuserver.utils.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class OrderManagerDTOMapper implements RowMapper<OrderManagerDTO, Order> {
    @Override
    public OrderManagerDTO mapRow(Order order) {
        try {
            OrderManagerDTO orderManagerDTO = new OrderManagerDTO();
            orderManagerDTO.setId(order.getId());
            orderManagerDTO.setGrandPrice(order.getGrandTotal());
            orderManagerDTO.setStatusOrder(order.getStatus());
            orderManagerDTO.setCreatedAt(StringUtil.convertTimestampToString(order.getCreatedAt()));
            orderManagerDTO.setAddress(new AddressDTOMapper().mapRow(order.getAddress()));
            orderManagerDTO.setUser(order.getUser());

            return orderManagerDTO;
        } catch (Exception ex) {
            return null;
        }
    }
}
