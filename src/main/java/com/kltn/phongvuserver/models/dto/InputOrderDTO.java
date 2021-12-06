package com.kltn.phongvuserver.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InputOrderDTO {
    private int userId;
    private int addressId;
    private BigDecimal subTotal;
    private BigDecimal shippingFee;
    private BigDecimal grandTotal;
    private BigDecimal discount;
    private String content;
    private int shipping;
    private int paymentMethod;
    private int status;
    private List<InputOrderItemDTO> listItem;
}
