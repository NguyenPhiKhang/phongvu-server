package com.kltn.phongvuserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryOrderDTO {
    List<SummaryOrderDTO> summaryOrder;
    List<OrderCardDTO> orderCard;
}
