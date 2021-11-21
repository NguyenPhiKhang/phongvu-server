package com.kltn.phongvuserver.models.embeddedID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductUserKey implements Serializable {
    @Column(name = "product_id")
    int productId;

    @Column(name = "user_id")
    int userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductUserKey)) return false;
        ProductUserKey that = (ProductUserKey) o;
        return getProductId() == that.getProductId() && getUserId() == that.getUserId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getUserId());
    }
}
