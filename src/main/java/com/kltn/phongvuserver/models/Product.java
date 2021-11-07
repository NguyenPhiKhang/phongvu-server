package com.kltn.phongvuserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "sku")
    private String sku;
    @Column(name = "description")
    private String description;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "visibility")
    private boolean visibility;
    @Column(name = "is_active")
    private boolean active;
    @Column(name = "promotion_percent")
    private int promotionPercent;
    @Column(name = "price_original")
    private BigDecimal priceOriginal;
    @Column(name = "order_count")
    private int orderCount;
    @Column(name = "is_free_ship")
    private boolean freeShip;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    @JsonIgnore
    private Brand brand;

    @OneToMany(targetEntity = ProductAttribute.class, mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProductAttribute> productAttributes = new HashSet<>();

    @ManyToMany(targetEntity = DataImage.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "image_product",
            joinColumns =
            @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<DataImage> dataImages = new HashSet<>();

    @ManyToMany(targetEntity = Demand.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "demand_product",
            joinColumns =
            @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "demand_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<Demand> demands = new HashSet<>();

    public void addAttribute(Attribute attribute, String value){
        productAttributes.add(new ProductAttribute(this, attribute, value));
    }

    public void addDemand(Demand demand){
        demands.add(demand);
    }

    public void addImages(List<DataImage> imgs){
        dataImages.addAll(imgs);
    }

//    @ManyToMany(targetEntity = OptionProductVarchar.class, cascade = CascadeType.ALL )
//    @JoinTable(
//            name = "catalog_product_varchar",
//            joinColumns =
//            @JoinColumn(name = "product_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"))
//    @JsonIgnore
//    private Set<OptionProductVarchar> optionProductVarchars = new HashSet<>();
//
//    @ManyToMany(targetEntity = OptionProductInteger.class, cascade = CascadeType.ALL )
//    @JoinTable(
//            name = "catalog_product_int",
//            joinColumns =
//            @JoinColumn(name = "product_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"))
//    @JsonIgnore
//    private Set<OptionProductInteger> optionProductIntegers = new HashSet<>();
//
//    @ManyToMany(targetEntity = OptionProductDecimal.class, cascade = CascadeType.ALL )
//    @JoinTable(
//            name = "catalog_product_decimal",
//            joinColumns =
//            @JoinColumn(name = "product_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"))
//    @JsonIgnore
//    private Set<OptionProductDecimal> optionProductDecimals = new HashSet<>();
//
//    @ManyToMany(targetEntity = Product.class, cascade = CascadeType.ALL )
//    @JoinTable(
//            name = "catalog_product_link",
//            joinColumns =
//            @JoinColumn(name = "product_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "link_product_id", referencedColumnName = "id"))
//    @JsonIgnore
//    private Set<Product> productLinks = new HashSet<>();
//
//    @ManyToMany(targetEntity = Product.class, mappedBy = "productLinks", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Set<Product> products = new HashSet<>();

//    @ManyToMany(targetEntity = Image.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinTable(
//            name = "images_products",
//            joinColumns =
//            @JoinColumn(name = "product_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"))
//
//    private Set<Image> images = new HashSet<>();

//    @ManyToMany(targetEntity = Option.class, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "option_product",
//            joinColumns =
//            @JoinColumn(name = "product_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "option_id", referencedColumnName = "id"))
//    @JsonIgnore
//    private Set<Option> options;

//    @OneToMany(targetEntity = Comment.class, mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Set<Comment> comments;
//
//    @OneToMany(targetEntity = Rating.class, mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Set<Rating> ratings;

//    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name="img_url", unique= true, referencedColumnName = "id")
//    private Image imgUrl;
}

