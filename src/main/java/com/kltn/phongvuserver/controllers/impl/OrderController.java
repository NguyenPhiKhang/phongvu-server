package com.kltn.phongvuserver.controllers.impl;

import com.kltn.phongvuserver.controllers.IOrderController;
import com.kltn.phongvuserver.mappers.impl.DetailOrderDTOMapper;
import com.kltn.phongvuserver.mappers.impl.OrderDTOMapper;
import com.kltn.phongvuserver.mappers.impl.OrderManagerDTOMapper;
import com.kltn.phongvuserver.models.dto.*;
import com.kltn.phongvuserver.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional(rollbackFor = Throwable.class)
@RequestMapping("/api/v1")
public class OrderController implements IOrderController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private DetailOrderDTOMapper detailOrderDTOMapper;

    @Autowired
    private OrderDTOMapper orderDTOMapper;

    @Autowired
    private OrderManagerDTOMapper orderManagerDTOMapper;

    @Override
    public String createOrder(InputOrderDTO orderInput) {
        orderService.createOrder(orderInput);
        return "Tạo đơn hàng thành công";
    }

    @Override
    public HistoryOrderDTO getListOrderByStatus(int userId, int status, int page, int pageSize) {
        List<SummaryOrderDTO> summaryOrder = orderService.getSummaryHistoryOrder(userId);
         List<OrderCardDTO> orderCard = orderService.getListOrderByStatus(userId, status, page, pageSize).stream()
                .map(value -> orderDTOMapper.mapRow(value)).collect(Collectors.toList());
         return new HistoryOrderDTO(summaryOrder, orderCard);
    }

    @Override
    public String updateStatusOfOrder(int orderId, int status) {
        orderService.updateStatusOfOrder(orderId, status);
        return "Cập nhật thành công.";
    }

    @Override
    public DetailOrderDTO getDetailOrder(int orderId) {
        return detailOrderDTOMapper.mapRow(orderService.getDetailOrder(orderId));
    }

    @Override
    public List<SummaryOrderDTO> getSummaryHistoryOrder(int userId) {
        return orderService.getSummaryHistoryOrder(userId);
    }

    @Override
    public List<OrderManagerDTO> getOrdersForAdmin(int userId, int status, String searchUser, int page, int pageSize) {
        return orderService.getOrdersFilterForAdmin(userId, status, searchUser, page, pageSize).stream()
                .map(value -> orderManagerDTOMapper.mapRow(value)).collect(Collectors.toList());
    }

    @Override
    public int countOrdersForAdmin(int userId, int status, String searchUser) {
        return orderService.countOrdersFilterForAdmin(userId, status, searchUser);
    }
}
