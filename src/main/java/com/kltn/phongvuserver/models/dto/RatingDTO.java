package com.kltn.phongvuserver.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RatingDTO {
    private int id;
    private int userId;
    private String userName;
    private String imageAvatar;
    private int star;
    private String comment;
    private List<FileRatingDTO> fileRating;
    private String timeUpdated;
}
