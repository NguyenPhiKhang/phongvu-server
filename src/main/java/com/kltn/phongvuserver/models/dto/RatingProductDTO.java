package com.kltn.phongvuserver.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class RatingProductDTO {
    private CountRatingProductDTO totalCount;
    private List<RatingDTO> data;
}
