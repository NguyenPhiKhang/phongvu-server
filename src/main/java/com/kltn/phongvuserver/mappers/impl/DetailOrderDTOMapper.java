package com.kltn.phongvuserver.mappers.impl;

import com.kltn.phongvuserver.mappers.RowMapper;
import com.kltn.phongvuserver.models.Order;
import com.kltn.phongvuserver.models.OrderItem;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.dto.DetailOrderDTO;
import com.kltn.phongvuserver.models.dto.OrderItemDTO;
import com.kltn.phongvuserver.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DetailOrderDTOMapper implements RowMapper<DetailOrderDTO, Order> {
    @Override
    public DetailOrderDTO mapRow(Order order) {

        try {
            DetailOrderDTO detailOrderDTO = new DetailOrderDTO();
            detailOrderDTO.setId(order.getId());
            detailOrderDTO.setAddress(new AddressDTOMapper().mapRow(order.getAddress()));
            detailOrderDTO.setShipping(order.getShipping());
            detailOrderDTO.setPayment(order.getPaymentMethod());
            detailOrderDTO.setGrandPrice(order.getGrandTotal());
            detailOrderDTO.setSubTotal(order.getSubTotal());
            detailOrderDTO.setShippingFee(order.getShippingFee());
            detailOrderDTO.setDiscount(order.getDiscount());
            detailOrderDTO.setContent(order.getContent());
            detailOrderDTO.setCreatedAt(StringUtil.convertTimestampToString(order.getCreatedAt()));
            detailOrderDTO.setUpdatedAt(StringUtil.convertTimestampToString(order.getUpdatedAt()));
            detailOrderDTO.setPayAt(order.getPayAt());
            detailOrderDTO.setUser(order.getUser());
            detailOrderDTO.setStatusOrder(order.getStatus());

            List<OrderItemDTO> orderItems = new ArrayList<>();

            for (OrderItem orderItem : order.getOrderItems()) {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setId(orderItem.getId());
                orderItemDTO.setPrice(orderItem.getPrice());
                orderItemDTO.setQuantity(orderItem.getQuantity());
                orderItemDTO.setImageUrl(orderItem.getImageUrl());
                Product product = orderItem.getProduct();
                orderItemDTO.setProductId(product.getId());
                orderItemDTO.setName(product.getName());
                orderItemDTO.setReviewStatus(orderItem.isReviewStatus());
                orderItemDTO.setOriginalPrice(orderItem.getOriginalPrice());

                orderItems.add(orderItemDTO);
            }

            detailOrderDTO.setListItem(orderItems);
            return detailOrderDTO;
        } catch (Exception ex) {
            return null;
        }
    }
}
