package com.kltn.phongvuserver.models.dto;

import com.kltn.phongvuserver.models.Payment;
import com.kltn.phongvuserver.models.Shipping;
import com.kltn.phongvuserver.models.StatusOrder;
import com.kltn.phongvuserver.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DetailOrderDTO {
    private int id;
    private AddressDTO address;
    private Shipping shipping;
    private Payment payment;
    private BigDecimal grandPrice;
    private BigDecimal subTotal;
    private BigDecimal shippingFee;
    private BigDecimal discount;
    private String content;
    private String createdAt;
    private String updatedAt;
    private Timestamp payAt;
    List<OrderItemDTO> listItem;
    private User user;
    private StatusOrder statusOrder;
}
