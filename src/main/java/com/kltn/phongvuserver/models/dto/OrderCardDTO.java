package com.kltn.phongvuserver.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderCardDTO {
    private int id;
    private String status;
    private String shipping;
    private String paymentMethod;
    private BigDecimal grandPrice;
    private List<OrderItemDTO> listItem;
}
