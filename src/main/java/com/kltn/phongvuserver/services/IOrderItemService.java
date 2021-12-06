package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Order;
import com.kltn.phongvuserver.models.OrderItem;
import com.kltn.phongvuserver.models.dto.InputOrderItemDTO;

import java.util.List;
import java.util.Set;

public interface IOrderItemService {
    boolean checkExistsOrderItem(int id);
    void save(InputOrderItemDTO orderItemDTO, Order order);
    void save(OrderItem orderItem);
    OrderItem getOrderItem(int id);
    void updateIsReview(int id, int reviewStatus);
    Set<OrderItem> mapListInputOrderItemDTOToOrderItem(List<InputOrderItemDTO> list, Order order);
    OrderItem mapInputOrderItemDTOToOrderItem(InputOrderItemDTO inputOrderItemDTO, Order order);
}
