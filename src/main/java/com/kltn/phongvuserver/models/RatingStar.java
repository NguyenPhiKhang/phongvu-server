package com.kltn.phongvuserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rating_star")
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RatingStar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @Column(name = "star1")
    private int star1;
    @Column(name = "star2")
    private int star2;
    @Column(name = "star3")
    private int star3;
    @Column(name = "star4")
    private int star4;
    @Column(name = "star5")
    private int star5;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "ratingStar")
    @JsonIgnore
    private Product product;

    public RatingStar(int id) {
        this.id = id;
    }

    public RatingStar(int id, int star1, int star2, int star3, int star4, int star5) {
        this.id = id;
        this.star1 = star1;
        this.star2 = star2;
        this.star3 = star3;
        this.star4 = star4;
        this.star5 = star5;
    }

    public RatingStar(int star1, int star2, int star3, int star4, int star5) {
        this.star1 = star1;
        this.star2 = star2;
        this.star3 = star3;
        this.star4 = star4;
        this.star5 = star5;
    }
}
