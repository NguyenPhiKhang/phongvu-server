package com.kltn.phongvuserver.models.recommendsystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AVGRatedProductDTO {
    private int productId;
    private double avgRated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AVGRatedProductDTO)) return false;
        AVGRatedProductDTO that = (AVGRatedProductDTO) o;
        return getProductId() == that.getProductId() &&
                Double.compare(that.getAvgRated(), getAvgRated()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getAvgRated());
    }
}
