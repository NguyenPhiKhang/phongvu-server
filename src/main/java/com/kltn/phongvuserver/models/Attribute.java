package com.kltn.phongvuserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "attributes")
@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attribute implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code")
    private String code;

    @Column(name = "label")
    private String label;

    @OneToMany(targetEntity = ProductAttribute.class, mappedBy = "attribute", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProductAttribute> productAttributes = new HashSet<>();

    public Attribute(String code, String label){
        this.code = code;
        this.label = label;
    }
}
