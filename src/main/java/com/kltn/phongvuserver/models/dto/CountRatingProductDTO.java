package com.kltn.phongvuserver.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CountRatingProductDTO {
    private int totalAll;
    private float percentStar;
    private int totalImage;
    private int totalStar1;
    private int totalStar2;
    private int totalStar3;
    private int totalStar4;
    private int totalStar5;

    public CountRatingProductDTO(int totalAll, int totalImage, int totalStar1, int totalStar2, int totalStar3, int totalStar4, int totalStar5) {
        this.totalAll = totalAll;
        this.totalImage = totalImage;
        this.totalStar1 = totalStar1;
        this.totalStar2 = totalStar2;
        this.totalStar3 = totalStar3;
        this.totalStar4 = totalStar4;
        this.totalStar5 = totalStar5;
    }

    public CountRatingProductDTO(int totalAll, int totalStar1, int totalStar2, int totalStar3, int totalStar4, int totalStar5) {
        this.totalAll = totalAll;
        this.totalStar1 = totalStar1;
        this.totalStar2 = totalStar2;
        this.totalStar3 = totalStar3;
        this.totalStar4 = totalStar4;
        this.totalStar5 = totalStar5;
    }

    public CountRatingProductDTO(int totalStar1, int totalStar2, int totalStar3, int totalStar4, int totalStar5) {
        this.totalStar1 = totalStar1;
        this.totalStar2 = totalStar2;
        this.totalStar3 = totalStar3;
        this.totalStar4 = totalStar4;
        this.totalStar5 = totalStar5;
    }

    public CountRatingProductDTO(int totalAll, float percentStar){
        this.totalAll = totalAll;
        this.percentStar = percentStar;
    }
}
