package com.kltn.phongvuserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ratings")
@NoArgsConstructor
@Getter
@Setter
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "comment")
    private String comment;
    @Column(name = "star")
    private int star;
    @Column(name = "incognito")
    private boolean incognito;
    @Column(name = "time_created")
    private Timestamp timeCreated;
    @Column(name = "time_updated")
    private Timestamp timeUpdated;

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @ManyToMany(targetEntity = DataImage.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "rating_image",
            joinColumns =
            @JoinColumn(name = "rating_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<DataImage> dataImages = new HashSet<>();
}
