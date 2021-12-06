package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.Order;
import com.kltn.phongvuserver.models.OrderItem;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.dto.OrderCardDTO;
import com.kltn.phongvuserver.models.dto.OrderItemDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDTOMapper implements RowMapper<OrderCardDTO, Order> {
    @Override
    public OrderCardDTO mapRow(Order order) {
        try {
            OrderCardDTO orderCardDTO = new OrderCardDTO();
            orderCardDTO.setId(order.getId());
            orderCardDTO.setStatus(order.getStatus().getName());
            orderCardDTO.setShipping(order.getShipping().getName());
            orderCardDTO.setGrandPrice(order.getGrandTotal());
            orderCardDTO.setPaymentMethod(order.getPaymentMethod().getName());
            orderCardDTO.setListItem(order.getOrderItems().stream()
            .map(orderItem -> {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setId(orderItem.getId());
                orderItemDTO.setPrice(orderItem.getPrice());
                orderItemDTO.setQuantity(orderItem.getQuantity());
                orderItemDTO.setImageUrl(orderItem.getImageUrl());
                orderItemDTO.setOriginalPrice(orderItem.getOriginalPrice());
                Product product = orderItem.getProduct();
                orderItemDTO.setProductId(product.getId());
                orderItemDTO.setName(product.getName());
                orderItemDTO.setReviewStatus(orderItem.isReviewStatus());

                return orderItemDTO;
            }).collect(Collectors.toList()));

            return orderCardDTO;
        } catch (Exception ex) {
            return null;
        }
    }
}
