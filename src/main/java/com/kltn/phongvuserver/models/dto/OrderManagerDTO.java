package com.kltn.phongvuserver.models.dto;

import com.kltn.phongvuserver.models.StatusOrder;
import com.kltn.phongvuserver.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderManagerDTO {
    private int id;
    private User user;
    private BigDecimal grandPrice;
    private StatusOrder statusOrder;
    private String createdAt;
    private AddressDTO address;
}
