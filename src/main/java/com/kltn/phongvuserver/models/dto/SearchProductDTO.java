package com.kltn.phongvuserver.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductDTO {
    private ProductItemDTO productItemDTO;
    private double cosine;
}
