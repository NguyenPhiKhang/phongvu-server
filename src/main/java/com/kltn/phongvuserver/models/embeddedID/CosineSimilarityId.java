package com.kltn.phongvuserver.models.embeddedID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CosineSimilarityId implements Serializable {
    private int row;
    private int column;
}