package com.kltn.phongvuserver.utils;

import com.kltn.phongvuserver.models.RatingStar;
import com.kltn.phongvuserver.models.dto.CalcRatingStarDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CommonUtil {
    public static BigDecimal calcPriceFinal(BigDecimal priceOriginal, int promotionPercent) {
        return priceOriginal.multiply(BigDecimal.valueOf(1.0 - (double) promotionPercent / 100.0));
    }

    public static CalcRatingStarDTO calcRatingStarProduct(RatingStar ratingStar) {
        CalcRatingStarDTO calcRatingStarDTO = new CalcRatingStarDTO(0, 0);
        if (ratingStar != null){
            int totalStar = ratingStar.getStar1() + ratingStar.getStar2() + ratingStar.getStar3() + ratingStar.getStar4() + ratingStar.getStar5();
            float percentStar = totalStar > 0 ? (float) (ratingStar.getStar1() + ratingStar.getStar2() * 2 + ratingStar.getStar3() * 3 + ratingStar.getStar4() * 4 + ratingStar.getStar5() * 5)
                    / totalStar : 0;
            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            calcRatingStarDTO.setTotalStar(totalStar);
            calcRatingStarDTO.setPercentStar(Float.parseFloat(decimalFormat.format(percentStar)));
        }

        return calcRatingStarDTO;
    }

    public static Pageable getPageForNativeQueryIsFalse(int page, int pageSize){
        return PageRequest.of(page - 1, pageSize);
    }

    public static int getPageForNativeQueryIsTrue(int page, int pageSize){
        return page < 1 ? 0 : (page - 1) * 20;
    }
}
