package com.kltn.phongvuserver.services;

import com.kltn.phongvuserver.models.Order;
import com.kltn.phongvuserver.models.dto.InputOrderDTO;
import com.kltn.phongvuserver.models.dto.SummaryOrderDTO;

import java.util.List;

public interface IOrderService {
    void createOrder(InputOrderDTO orderInput);
    List<Order> getListOrderByStatus(int userId, int status, int page, int pageSize);
    void updateStatusOfOrder(int orderId, int status);
    Order getDetailOrder(int orderId);
    List<Order> getOrdersFilterForAdmin(int userId, int statusId, String searchUser, int page, int pageSize);
    int countOrdersFilterForAdmin(int userId, int statusId, String searchUser);
    List<SummaryOrderDTO> getSummaryHistoryOrder(int userId);
}
