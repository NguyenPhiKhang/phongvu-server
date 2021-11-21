package com.kltn.phongvuserver.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kltn.phongvuserver.models.embeddedID.ProductUserKey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "favorites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Favorite implements Serializable {
    @EmbeddedId
    private ProductUserKey id;

    @Column(name = "liked")
    private boolean liked;
    @Column(name = "time_updated")
    private Timestamp timeUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
}