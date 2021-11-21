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
@Table(name = "seen_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SeenProduct implements Serializable {
    @EmbeddedId
    private ProductUserKey id;

    @Column(name ="last_time")
    private Timestamp lastTime;
    @Column(name ="count")
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
}
