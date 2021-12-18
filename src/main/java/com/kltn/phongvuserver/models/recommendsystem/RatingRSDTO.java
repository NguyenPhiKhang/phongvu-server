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
public class RatingRSDTO {
    private int userId;
    private int productId;
    private double value;

    public RatingRSDTO(int userId, int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "RatingRSDTO{" +
                "userId=" + userId +
                ", productId=" + productId +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingRSDTO)) return false;
        RatingRSDTO that = (RatingRSDTO) o;
        return (getUserId()== that.getUserId()) &&
                getProductId() == that.getProductId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getProductId(), getValue());
    }
}
