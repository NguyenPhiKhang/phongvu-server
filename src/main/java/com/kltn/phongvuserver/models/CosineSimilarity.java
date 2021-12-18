package com.kltn.phongvuserver.models;

import com.kltn.phongvuserver.models.embeddedID.CosineSimilarityId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cosine_similarity")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(CosineSimilarityId.class)
public class CosineSimilarity implements Serializable {
    @Id
    @Column(name = "row_product_id")
    private int row;
    @Id
    @Column(name = "column_product_id")
    private int column;

    @Column(name = "similarity")
    private double cosSimilarity;
}
