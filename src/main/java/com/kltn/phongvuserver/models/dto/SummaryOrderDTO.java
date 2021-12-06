package com.kltn.phongvuserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryOrderDTO {
    private Integer statusId;
    private String statusName;
    private Long total;
}
