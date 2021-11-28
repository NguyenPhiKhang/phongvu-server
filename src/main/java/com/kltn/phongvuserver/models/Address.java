package com.kltn.phongvuserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Address implements Serializable {
    @Id
    private int id;
    @Column(name = "specific_address")
    private String specificAddress;
    @Column(name = "name")
    private String name;
    @Column(name = "number_phone")
    private String numberPhone;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updateAt;
    @Column(name = "default_is")
    private boolean defaultIs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private Ward ward;

//    @OneToMany(targetEntity = Order.class, mappedBy = "address", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Set<Order> orders;

    public Address(String specificAddress, String name, String numberPhone, boolean defaultIs, Timestamp createdAt, Timestamp updateAt) {
        this.specificAddress = specificAddress;
        this.name = name;
        this.numberPhone = numberPhone;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.defaultIs = defaultIs;
    }
}
