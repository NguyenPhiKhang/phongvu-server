package com.kltn.phongvuserver.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CardMyRatingDTO {
    private int id;
    private int star;
    private int idProduct;
    private String nameProduct;
    private String imageProduct;
    private String comment;
    private List<FileRatingDTO> fileRating;
    private String timeUpdated;
}
