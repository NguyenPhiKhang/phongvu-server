package com.kltn.phongvuserver.services.ipml;

import com.kltn.phongvuserver.models.Order;
import com.kltn.phongvuserver.models.OrderItem;
import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.StatusOrder;
import com.kltn.phongvuserver.models.dto.InputOrderDTO;
import com.kltn.phongvuserver.models.dto.InputOrderItemDTO;
import com.kltn.phongvuserver.models.dto.SummaryOrderDTO;
import com.kltn.phongvuserver.repositories.CartItemRepository;
import com.kltn.phongvuserver.repositories.OrderRepository;
import com.kltn.phongvuserver.repositories.ProductRepository;
import com.kltn.phongvuserver.repositories.StatusOrderRepository;
import com.kltn.phongvuserver.services.*;
import com.kltn.phongvuserver.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Throwable.class)
public class OrderService implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private IOrderItemService orderItemService;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IShippingService shippingService;

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IStatusOrderService statusOrderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StatusOrderRepository statusOrderRepository;

    @Autowired
    private ICartService cartService;
//
//    @Autowired
//    private IFlashSaleProductService flashSaleProductService;

    @Override
    public void createOrder(InputOrderDTO orderInput) {
        Order newOrder = new Order();

        Random rd = new Random();

        int idOrder;
        do {
            idOrder = 100 + rd.nextInt(6000001);
        } while (orderRepository.existsById(idOrder));

        newOrder.setId(idOrder);
        newOrder.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        newOrder.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        newOrder.setAddress(addressService.getAddressById(orderInput.getAddressId()));
        newOrder.setUser(userService.getUserById(orderInput.getUserId()));
        newOrder.setSubTotal(orderInput.getSubTotal());
        newOrder.setShippingFee(orderInput.getShippingFee());
        newOrder.setGrandTotal(orderInput.getGrandTotal());
        newOrder.setDiscount(orderInput.getDiscount());
        newOrder.setContent(orderInput.getContent());
        newOrder.setShipping(shippingService.getShippingById(orderInput.getShipping()));
        newOrder.setPaymentMethod(paymentService.getPaymentById(orderInput.getPaymentMethod()));
        newOrder.setStatus(statusOrderService.getStatusOrderById(orderInput.getStatus()));
        newOrder.setOrderItems(orderItemService.mapListInputOrderItemDTOToOrderItem(orderInput.getListItem(), newOrder));

        for(InputOrderItemDTO inputOrderItemDTO: orderInput.getListItem()){
            cartService.removeProductInCart(inputOrderItemDTO.getCartItemId());
        }

        orderRepository.save(newOrder);
    }

    @Override
    public List<Order> getListOrderByStatus(int userId, int status, int page, int pageSize) {
        Pageable pageable = CommonUtil.getPageForNativeQueryIsFalse(page, pageSize);
        return orderRepository.findAllByUserIdAndStatusIdFetch(userId, status, pageable);
    }

    @Override
    public void updateStatusOfOrder(int orderId, int status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return;
        if (status == 5 || status == 6) {
            for (OrderItem orderItem : order.getOrderItems()) {
                Product product = orderItem.getProduct();
                product.setOrderCount(Math.max(product.getOrderCount() - 1, 0));
                product.setQuantity(product.getQuantity() + orderItem.getQuantity());
                productRepository.save(product);
            }
        }

        if (status == 4) {
            order.setPayAt(new Timestamp(System.currentTimeMillis()));
        }

        order.setStatus(statusOrderService.getStatusOrderById(status));
        order.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        orderRepository.save(order);
    }

    @Override
    public Order getDetailOrder(int orderId) {
        return orderRepository.findOrderDetailById(orderId);
    }

    @Override
    public List<Order> getOrdersFilterForAdmin(int userId, int statusId, String searchUser, int page, int pageSize) {
//        int pageNew = page < 1 ? 0 : (page - 1) * pageSize;
//        return orderRepository.findOrdersFilterForAdmin(userId, statusId, searchUser, pageNew, pageSize);

        return null;
    }

    @Override
    public int countOrdersFilterForAdmin(int userId, int statusId, String searchUser) {
//        return orderRepository.countOrdersFilterForAdmin(userId, statusId, searchUser);
        return 0;
    }

    @Override
    public List<SummaryOrderDTO> getSummaryHistoryOrder(int userId) {
        List<SummaryOrderDTO> listAvailableSummary = orderRepository.getSummaryHistoryOrder(userId);
        return statusOrderRepository.findAll().stream()
                .map(a->{
                    SummaryOrderDTO summaryOrderDTO = listAvailableSummary.stream().filter(sm->sm.getStatusId().equals(a.getId())).findFirst().orElse(null);
                    return new SummaryOrderDTO(a.getId(), a.getName(), summaryOrderDTO==null?0L:summaryOrderDTO.getTotal());
                })
                .sorted(Comparator.comparing(SummaryOrderDTO::getStatusId))
                .collect(Collectors.toList());
    }
}
