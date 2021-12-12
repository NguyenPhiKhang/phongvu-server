package com.kltn.phongvuserver.services.ipml;


import com.kltn.phongvuserver.models.Product;
import com.kltn.phongvuserver.models.Rating;
import com.kltn.phongvuserver.models.RatingStar;
import com.kltn.phongvuserver.models.User;
import com.kltn.phongvuserver.models.dto.CountRatingProductDTO;
import com.kltn.phongvuserver.repositories.ProductRepository;
import com.kltn.phongvuserver.repositories.RatingRepository;
import com.kltn.phongvuserver.repositories.RatingStarRepository;
import com.kltn.phongvuserver.repositories.UserRepository;
import com.kltn.phongvuserver.services.IRatingService;
import com.kltn.phongvuserver.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Service
@Transactional(rollbackFor = Throwable.class)
public class RatingService implements IRatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RatingStarRepository ratingStarRepository;

    @Override
    public void autoRating() {
        Random rd = new Random();
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findProductsByVisibilityTrue();

        int size_product = products.size() - 100;

        System.out.println("size product: " + size_product);

        int q = 100;

        for (int j = 0; j < size_product; j++) {
            Set<Rating> listRatings = new HashSet<>();
            Product pd = products.get(j);
            System.out.println("-----------------product thu: " + j + "--- id: " + pd.getId() + " ------------------------");
            int rating_rd = rd.nextInt(401);
            rating_rd = q > 0 ? rating_rd : rating_rd + q;
            q *= -1;
            int total_rating = rating_rd < 0 ? 0 : rating_rd == 0 ? 500 + rd.nextInt(611) : Math.min(rating_rd, 400);
            System.out.println("tong rating: " + total_rating);
            int star1 = 0;
            int star2 = 0;
            int star3 = 0;
            int star4 = 0;
            int star5 = 0;
            for (int i = 0; i < total_rating; i++) {
//                int idRating;
//                do {
//                    idRating = 10000000 + rd.nextInt(6000001);
//                } while (ratingRepository.existsById(idRating));

                int star = 1 + rd.nextInt(11);
                switch (star) {
                    case 1:
                        star1++;
                        break;
                    case 2:
                        star2++;
                        break;
                    case 3:
                        star3++;
                        break;
                    case 4:
                        star4++;
                        break;
                    default:
                        star5++;
                        break;

                }

                int userIndex;
                User user;
//                do {
                userIndex = rd.nextInt(users.size());
                user = users.get(userIndex);
//                } while (ratingRepository.existsByUserIdAndProductId(user.getId(), pd.getId()));


                Rating rating = new Rating();
//                rating.setId(idRating);
                rating.setStar(Math.min(star, 5));
                rating.setUser(user);
                rating.setProduct(pd);
                rating.setTimeCreated(new Timestamp(System.currentTimeMillis()));
                rating.setTimeUpdated(new Timestamp(System.currentTimeMillis()));

                listRatings.add(rating);
            }

//            int idRatingStar;
//            do {
//                idRatingStar = 100 + rd.nextInt(6000001);
//            } while (ratingRepository.existsById(idRatingStar));

            RatingStar ratingStar = new RatingStar(star1, star2, star3, star4, star5);

            pd.setRatingStar(ratingStar);
            pd.setRatings(listRatings);

            productRepository.save(pd);
        }
    }

    @Override
    public boolean checkExistId(int id) {
        return ratingRepository.existsById(id);
    }

    @Override
    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public CountRatingProductDTO countRatingByStarOfProduct(int productId) {
        return ratingRepository.countRatingByStar(productId);
    }

    @Override
    public int countRatingByImageOfProduct(int productId) {
        return ratingRepository.countRatingByImage(productId);
    }

    @Override
    public List<Rating> getRatingsByProductId(int productId, int page) {
        return ratingRepository.findRatingsByProductId(productId, page);
    }

    @Override
    public List<Rating> getRatingByProductIdAndStar(int productId, int star, int page) {
        return ratingRepository.findRatingsByProductIdAndStar(productId, star, page);
    }

    @Override
    public List<Rating> getRatingByProductIdHasImage(int productId, int page) {
        return ratingRepository.findRatingsByProductIdAndHasImage(productId, page);
    }

    @Override
    public List<Rating> getRatingByUserAndStar(int userId, int star, int page, int pageSize) {
        Pageable pageable = CommonUtil.getPageForNativeQueryIsFalse(page, pageSize);
        return ratingRepository.findRatingByUserAndStar(userId, star, pageable);
    }

    @Override
    public CountRatingProductDTO countStarRatingByUser(int id) {
        CountRatingProductDTO countRatingProductDTO = new CountRatingProductDTO();

        List<Object[]> objects = ratingRepository.countStarRatingByUser(id);
        int total = 0;
        float totalPercent = 0;

        for (Object[] o : objects) {
            switch ((int) o[0]) {
                case 1:
                    countRatingProductDTO.setTotalStar1(((BigInteger) o[1]).intValue());
                    total += ((BigInteger) o[1]).intValue();
                    totalPercent += ((BigInteger) o[1]).intValue() * 1.0;
                    break;
                case 2:
                    countRatingProductDTO.setTotalStar2(((BigInteger) o[1]).intValue());
                    total += ((BigInteger) o[1]).intValue();
                    totalPercent += ((BigInteger) o[1]).intValue() * 2.0;
                    break;
                case 3:
                    countRatingProductDTO.setTotalStar3(((BigInteger) o[1]).intValue());
                    total += ((BigInteger) o[1]).intValue();
                    totalPercent += ((BigInteger) o[1]).intValue() * 3.0;
                    break;
                case 4:
                    countRatingProductDTO.setTotalStar4(((BigInteger) o[1]).intValue());
                    total += ((BigInteger) o[1]).intValue();
                    totalPercent += ((BigInteger) o[1]).intValue() * 4.0;
                    break;
                case 5:
                    countRatingProductDTO.setTotalStar5(((BigInteger) o[1]).intValue());
                    total += ((BigInteger) o[1]).intValue();
                    totalPercent += ((BigInteger) o[1]).intValue() * 5.0;
                    break;
            }
        }
        countRatingProductDTO.setTotalAll(total);
        countRatingProductDTO.setPercentStar(totalPercent / total);
        return countRatingProductDTO;
    }
}
